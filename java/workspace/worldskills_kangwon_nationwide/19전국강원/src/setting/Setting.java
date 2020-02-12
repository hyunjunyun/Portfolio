package setting;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class Setting extends JFrame {
	
	Connection con;
	Statement stmt;
	JTextPane log = new JTextPane(); 
	SimpleAttributeSet blueAttr = new SimpleAttributeSet(log.getInputAttributes());
	SimpleAttributeSet redAttr = new SimpleAttributeSet(log.getInputAttributes());
	SimpleAttributeSet blackAttr = new SimpleAttributeSet(log.getInputAttributes());
	SimpleAttributeSet blueBoldAttr;
	SimpleAttributeSet redBoldAttr;
	HashMap<String, Integer> memberMap = new HashMap<String, Integer>();
	HashMap<String, Integer> bookMap = new HashMap<String, Integer>();
	PreparedStatement pstMember;
	PreparedStatement pstBook;
	PreparedStatement pstOrderlog;
	PreparedStatement pstSurveyCategory;
	PreparedStatement pstSurveyResults;
	
	public Setting() {
		setSize(300, 300);
		setDefaultCloseOperation(2);
		setLocationRelativeTo(null);
		setTitle("bookdb 초기화");
		
		StyleConstants.setForeground(blueAttr, Color.BLUE);
		StyleConstants.setForeground(redAttr, Color.RED);

		blueBoldAttr = new SimpleAttributeSet(blueAttr.copyAttributes());
		redBoldAttr = new SimpleAttributeSet(redAttr.copyAttributes());
		
		StyleConstants.setBold(blueBoldAttr, true);
		StyleConstants.setBold(redBoldAttr, true);
		
		log.setBorder(BorderFactory.createTitledBorder("Log"));
		
		var memberColumns = "id,login_id,login_pwd,birthdate,authority,phone,email".split(",");
		
		for (int i = 0; i < memberColumns.length; i++) {
			memberMap.put(memberColumns[i], i + 1);			
		}
		
		var bookColumns = "id,sub_category_id,name,image,stock,price,author,intro,numpages,isbn,created_by,hashtag".split(",");
		
		for(int i = 0; i<bookColumns.length; i++) {
			bookMap.put(bookColumns[i], i+1);
		}

		add(new JScrollPane(log));
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				new Thread(() -> init()).start();
			}
		});
		
	}
	
	private void init() {
		
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/?serverTimezone=UTC", "root", "1234");
			stmt = con.createStatement();
			
			pstMember = con.prepareStatement("INSERT INTO `member` VALUES(?, ?, MD5(?), ?, ?, ?, ?)");
			pstBook = con.prepareStatement("INSERT INTO `book` VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			pstOrderlog = con.prepareStatement("INSERT INTO `order_log` VALUES(?, ?, ?, ?, ?)");
			pstSurveyCategory = con.prepareStatement("INSERT INTO `survey_category` VALUES(?, ?, ?)");
			pstSurveyResults = con.prepareStatement("INSERT INTO `survey_results` VALUES(?, ?, ?, ?)");
			
			runSQL("DROP DATABASE IF EXISTS bookdb", "DB 제거");
			runSQL("CREATE DATABASE bookdb DEFAULT CHARACTER SET utf8", "DB 생성");
			
			stmt.execute("USE `bookdb` ;");
			
			runSQL("CREATE TABLE IF NOT EXISTS `bookdb`.`main_category` (\r\n" + 
					"  `id` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + 
					"  `name` VARCHAR(20) CHARACTER SET 'utf8' NOT NULL,\r\n" + 
					"  PRIMARY KEY (`id`))\r\n" + 
					"ENGINE = InnoDB\r\n" + 
					"DEFAULT CHARACTER SET = utf8;\r\n" + 
					"", "main_category 테이블 생성");
			
			runSQL("CREATE TABLE IF NOT EXISTS `bookdb`.`sub_category` (\r\n" + 
					"  `id` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + 
					"  `main_category_id` INT(11) NOT NULL,\r\n" + 
					"  `name` VARCHAR(20) CHARACTER SET 'utf8' NOT NULL,\r\n" + 
					"  PRIMARY KEY (`id`),\r\n" + 
					"  INDEX `FK_main_category_sub_category_main_category_id_idx` (`main_category_id` ASC) VISIBLE,\r\n" + 
					"  CONSTRAINT `FK_main_category_sub_category_main_category_id`\r\n" + 
					"    FOREIGN KEY (`main_category_id`)\r\n" + 
					"    REFERENCES `bookdb`.`main_category` (`id`)\r\n" + 
					"    ON DELETE RESTRICT\r\n" + 
					"    ON UPDATE RESTRICT)\r\n" + 
					"ENGINE = InnoDB\r\n" + 
					"DEFAULT CHARACTER SET = utf8;", "sub_category 테이블 생성");
			
			runSQL("CREATE TABLE IF NOT EXISTS `bookdb`.`book` (\r\n" + 
					"  `id` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + 
					"  `sub_category_id` INT(11) NOT NULL,\r\n" + 
					"  `name` NVARCHAR(45) NOT NULL,\r\n" + 
					"  `image` MEDIUMBLOB NOT NULL,\r\n" + 
					"  `stock` INT(11) NOT NULL,\r\n" + 
					"  `price` INT(11) NOT NULL, " +
					"  `author` NVARCHAR(45) NOT NULL, " +
					"  `intro` TEXT NOT NULL, " +
					"  `numpages` INT(11) NOT NULL, " +
					"  `isbn` NVARCHAR(25) NOT NULL, " +
					"  `created_by` DATE NOT NULL, " +
					"  `hashtag` TEXT NULL DEFAULT NULL,\r\n" + 
					"  PRIMARY KEY (`id`),\r\n" + 
					"  INDEX `FK_book_sub_category_sub_category_id_idx` (`sub_category_id` ASC) VISIBLE,\r\n" + 
					"  CONSTRAINT `FK_book_sub_category_sub_category_id`\r\n" + 
					"    FOREIGN KEY (`sub_category_id`)\r\n" + 
					"    REFERENCES `bookdb`.`sub_category` (`id`))\r\n" + 
					"ENGINE = InnoDB\r\n" + 
					"DEFAULT CHARACTER SET = utf8;", "book 테이블 생성");
			
			runSQL("CREATE TABLE IF NOT EXISTS `bookdb`.`member` (\r\n" + 
					"  `id` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + 
					"  `login_id` VARCHAR(20) CHARACTER SET 'utf8' NOT NULL,\r\n" + 
					"  `login_pwd` VARCHAR(32) CHARACTER SET 'utf8' NOT NULL,\r\n" + 
					"  `birthdate` DATE NOT NULL,\r\n" + 
					"  `authority` VARCHAR(5) CHARACTER SET 'utf8' NOT NULL,\r\n" + 
					"  `phone` VARCHAR(15) CHARACTER SET 'utf8' NOT NULL,\r\n" + 
					"  `email` VARCHAR(50) CHARACTER SET 'utf8' NOT NULL,\r\n" + 
					"  PRIMARY KEY (`id`),\r\n" + 
					"  UNIQUE INDEX `login_id_UNIQUE` (`login_id` ASC) VISIBLE,\r\n" + 
					"  UNIQUE INDEX `phone_UNIQUE` (`phone` ASC) VISIBLE,\r\n" + 
					"  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)\r\n" + 
					"ENGINE = InnoDB\r\n" + 
					"AUTO_INCREMENT = 15\r\n" + 
					"DEFAULT CHARACTER SET = utf8;", "member 테이블 생성");
			
			runSQL("CREATE TABLE IF NOT EXISTS `bookdb`.`order_log` (\r\n" + 
					"  `id` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + 
					"  `book_id` INT(11) NOT NULL,\r\n" + 
					"  `member_id` INT(11) NOT NULL,\r\n" + 
					"  `quantity` INT(11) NOT NULL,\r\n" + 
					"  `order_time` DATETIME NOT NULL,\r\n" + 
					"  PRIMARY KEY (`id`),\r\n" + 
					"  INDEX `FK_book_order_log_book_id_idx` (`book_id` ASC) VISIBLE,\r\n" + 
					"  INDEX `FK_member_order_log_member_id_idx` (`member_id` ASC) VISIBLE,\r\n" + 
					"  CONSTRAINT `FK_book_order_log_book_id`\r\n" + 
					"    FOREIGN KEY (`book_id`)\r\n" + 
					"    REFERENCES `bookdb`.`book` (`id`),\r\n" + 
					"  CONSTRAINT `FK_member_order_log_member_id`\r\n" + 
					"    FOREIGN KEY (`member_id`)\r\n" + 
					"    REFERENCES `bookdb`.`member` (`id`))\r\n" + 
					"ENGINE = InnoDB\r\n" + 
					"DEFAULT CHARACTER SET = utf8;", "order_log 테이블 생성");
			
			runSQL("CREATE TABLE IF NOT EXISTS `bookdb`.`survey_category` (\r\n" + 
					"  `id` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + 
					"  `group` NVARCHAR(5) NOT NULL,\r\n" + 
					"  `description` VARCHAR(80) CHARACTER SET 'utf8' NOT NULL,\r\n" + 
					"  PRIMARY KEY (`id`),\r\n" + 
					"  INDEX `IDX_survey_category_group` USING BTREE (`group`) VISIBLE)\r\n" + 
					"ENGINE = InnoDB\r\n" + 
					"DEFAULT CHARACTER SET = utf8;", "survey_category 테이블 생성");
			
			runSQL("CREATE TABLE IF NOT EXISTS `bookdb`.`survey_results` (\r\n" + 
					"  `id` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + 
					"  `member_id` INT(11) NOT NULL,\r\n" + 
					"  `survey_category_id` INT(11) NOT NULL,\r\n" + 
					"  `rating` INT(11) NOT NULL,\r\n" + 
					"  PRIMARY KEY (`id`),\r\n" + 
					"  INDEX `fk_survey_results_member1_idx` (`member_id` ASC) VISIBLE,\r\n" + 
					"  INDEX `fk_survey_results_survey_category1_idx` (`survey_category_id` ASC) VISIBLE,\r\n" + 
					"  CONSTRAINT `fk_survey_results_member1`\r\n" + 
					"    FOREIGN KEY (`member_id`)\r\n" + 
					"    REFERENCES `bookdb`.`member` (`id`),\r\n" + 
					"  CONSTRAINT `fk_survey_results_survey_category1`\r\n" + 
					"    FOREIGN KEY (`survey_category_id`)\r\n" + 
					"    REFERENCES `bookdb`.`survey_category` (`id`))\r\n" + 
					"ENGINE = InnoDB\r\n" + 
					"DEFAULT CHARACTER SET = utf8;", "survey_results 테이블 생성");
			
			runSQL("DROP USER IF EXISTS 'user'@'localhost'", "DB 유저 제거");
			runSQL("CREATE USER 'user'@'localhost' IDENTIFIED BY '1234'", "DB 유저 생성");
			runSQL("GRANT SELECT, UPDATE, DELETE, INSERT ON bookdb.* TO 'user'@'localhost'", "DB 권한 부여");
			
			importMember();
			importCategoriesAndBooks();
			
			importCsv(pstSurveyCategory, "./지급자료/survey_category.csv", "설문 항목 데이터 생성");
			importCsv(pstOrderlog, "./지급자료/order_log.csv", "주문 내역 데이터 생성");
			importCsv(pstSurveyResults, "./지급자료/survey_results.csv", "설문 결과 데이터 생성");
			
			insertLog("DB 초기화 성공", blueBoldAttr);
			
		} catch (Exception e1) {
			insertLog("DB 초기화 실패", redBoldAttr);
			
			try {
				stmt.execute("DROP DATABASE IF EXISTS bookdb");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} finally {
			try {
				for (int i = 5; i > 0; i--) {
					insertLog(i + "초후 프로그램이 종료됩니다.", blackAttr);
					Thread.sleep(1000);
				}
				
				dispose();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
		}
		
	}
	
	private void importCsv(PreparedStatement pst, String name, String caption) throws Exception {
		
		try {
			var lines = Files.readAllLines(Paths.get(name));
			
			for (int i = 1; i < lines.size(); i++) {
				var split = lines.get(i).split(",");
				
				for (int j = 0; j < split.length; j++) {
					pst.setObject(j + 1, split[j]);
				}
				
				pst.execute();
			}
			
			insertLog(caption + " 성공", blueAttr);
			
		} catch (Exception e) {
			insertLog(caption + " 실패", redAttr);
			insertLog(name + " (지정된 파일을 찾을 수 없습니다)", redAttr);
			e.printStackTrace();
			throw e;
		}
	}
	
	private void importCategoriesAndBooks() throws Exception {
		int mainId = 0;
		int subId = 0;
		int bookId = 0;
		String name = "";
		String subName = "";
		
		try {
			for(var path : Files.list(Paths.get("./지급자료/categories")).toArray(Path[]::new)) {
				
				name = path.getName(path.getNameCount() - 1).toString();
				mainId++;
				
				insertLog("main_category " + name + " 추가 시작", blueAttr);
				stmt.execute("INSERT INTO main_category VALUES(" + mainId + ", '" + name + "')");
				
				for(var subPath : Files.list(path).toArray(Path[]::new)) {
					subName = subPath.getName(subPath.getNameCount() - 1).toString();
					
					subId++;
					
					insertLog("ㄴ sub_category " + subName + " 추가 시작", blueAttr);
					stmt.execute("INSERT INTO sub_category VALUES(" + subId + ", " + mainId + ", '" + subName + "')");
					
					for(var bookPath : Files.list(subPath).toArray(Path[]::new)) {
						var bookName = bookPath.getName(bookPath.getNameCount() - 1).toString();
						
						if (bookName.endsWith("txt")) {
							bookId++;
							
							pstBook.setObject(1, bookId);
							pstBook.setObject(2, subId);
							importTxtData(pstBook, bookMap, Files.readAllLines(bookPath));
							
							pstBook.setBinaryStream(4, new FileInputStream(bookPath.toString().replace("txt", "jpg")));
							pstBook.execute();
							insertLog(" ㄴ " + bookName + " 추가 완료", blueAttr);
							
						}
					}
					
				}
				
			}
			
		} catch (Exception e) {
			insertLog(e.getMessage(), redAttr);
			insertLog("ㄴ sub_category " + subName + " 추가 실패", redAttr);
			insertLog("main_category " + name + " 추가 실패", redAttr);
			e.printStackTrace();
			throw e;
		}
	}
	
	private void importMember() {
		
		try {
			for(var path : Files.list(Paths.get("./지급자료/member_list")).toArray(Path[]::new)) {
				importTxtData(pstMember, memberMap, Files.readAllLines(path));
				pstMember.execute();
			}
			
			insertLog("회원 기본 데이터 생성 성공", blueAttr);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void importTxtData(PreparedStatement pst, HashMap<String, Integer> map, List<String> lines) {
		
		try {
			
			for (int i = 0; i < lines.size(); i++) {
				var split = lines.get(i).split("\t");
				
				pst.setObject(map.get(split[0]), split[1]);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
	}
	
	private void insertLog(String text, SimpleAttributeSet attr) {
		
		SwingUtilities.invokeLater( () -> {
			try {
				log.getStyledDocument().insertString(log.getDocument().getLength(), text + "\n", attr);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
			log.setSelectionStart(log.getDocument().getLength());
			log.setSelectionEnd(log.getDocument().getLength());			
		} );
		
	}
	
	private void runSQL(String sql, String caption) {
		try {
			stmt.execute(sql);
			insertLog(caption + " 성공", blueAttr);
		} catch (Exception e) {
			insertLog(caption + " 실패", redAttr);
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Setting().setVisible(true);
	}
}
