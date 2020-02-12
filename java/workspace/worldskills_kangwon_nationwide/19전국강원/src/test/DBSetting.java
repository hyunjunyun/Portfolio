package test;

import java.awt.Color;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class DBSetting extends JFrame {

   JTextPane log = new JTextPane();
   
   public static Connection con;
   public static Statement stmt;
   
   public static PreparedStatement pstMember;
   public static PreparedStatement pstBook;
   public static PreparedStatement pstSurvey;
   public static PreparedStatement pstorder;
   public static PreparedStatement pstSurveyResult;
   
   SimpleAttributeSet blueAttr = new SimpleAttributeSet(log.getInputAttributes());
   SimpleAttributeSet blueBoldAttr = new SimpleAttributeSet(log.getInputAttributes());
   SimpleAttributeSet redAttr = new SimpleAttributeSet(log.getInputAttributes());
   SimpleAttributeSet redBoldAttr = new SimpleAttributeSet(log.getInputAttributes());
   SimpleAttributeSet blackAttr = new SimpleAttributeSet(log.getInputAttributes());
   
   HashMap<String, Integer> memberMap = new HashMap<String, Integer>();
   HashMap<String, Integer> bookMap = new HashMap<String, Integer>();
   
   static {
      try {
         con = DriverManager.getConnection("jdbc:mysql://localhost?serverTimezone=UTC", "root", "1234");
         stmt = con.createStatement();
      } catch (Exception e) {
         // TODO: handle exception
      }
   }
   
   public DBSetting() {
      // TODO Auto-generated constructor stub
      setTitle("bookdb 초기화");
      setSize(300, 300);
      setDefaultCloseOperation(2);
      setLocationRelativeTo(null);
      
      StyleConstants.setForeground(blueAttr, Color.blue);
      StyleConstants.setForeground(blueBoldAttr, Color.blue);
      StyleConstants.setForeground(redAttr, Color.red);
      StyleConstants.setForeground(redBoldAttr, Color.red);
      StyleConstants.setBold(blueBoldAttr, true);
      StyleConstants.setBold(redBoldAttr, true);
      
      add(new JScrollPane(log));
      log.setBorder(new TitledBorder("Log"));
      
      var md = "id,sub_category_id,name,image,stock,price,author,intro,numpages,isbn,created_by,hashtag".split(",");
      for (int i = 0; i < md.length; i++) {
         bookMap.put(md[i], i + 1);
      }
      
      md = "id,login_id,login_pwd,birthdate,authority,phone,email".split(",");
      for (int i = 0; i < md.length; i++) {
         memberMap.put(md[i], i + 1);
      }
      
      try {
         pstMember = con.prepareStatement("insert into `member` values(?, ?, md5(?), ?, ?, ?, ?)");
         pstBook = con.prepareStatement("insert into book values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
         pstSurvey = con.prepareStatement("insert into survey_category values(?, ?, ?)");
         pstSurveyResult = con.prepareStatement("insert into survey_results values(?, ?, ?, ?)");
         pstorder = con.prepareStatement("insert into order_log values(?, ?, ?, ?, ?)");
      } catch (Exception e) {
         // TODO: handle exception
         e.printStackTrace();
      }
      
      setVisible(true);
      
      new Thread(() -> init()).start();
   }
   
   public void init() {
      try {
         runSQL("drop database if exists bookdb", "DB 제거");
         runSQL("CREATE SCHEMA IF NOT EXISTS `bookdb` DEFAULT CHARACTER SET utf8 ;", "DB 생성");
         stmt.execute("use bookdb");
         runSQL("CREATE TABLE IF NOT EXISTS `bookdb`.`main_category` (\r\n" + 
               "  `id` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + 
               "  `name` VARCHAR(20) NOT NULL,\r\n" + 
               "  PRIMARY KEY (`id`))\r\n" + 
               "ENGINE = InnoDB;", "main_category 테이블 생성");
         runSQL("CREATE TABLE IF NOT EXISTS `bookdb`.`sub_category` (\r\n" + 
               "  `id` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + 
               "  `main_category_id` INT(11) NOT NULL,\r\n" + 
               "  `name` VARCHAR(20) NOT NULL,\r\n" + 
               "  PRIMARY KEY (`id`),\r\n" + 
               "  INDEX `fk_sc_mid_idx` (`main_category_id` ASC),\r\n" + 
               "  CONSTRAINT `fk_sc_mid`\r\n" + 
               "    FOREIGN KEY (`main_category_id`)\r\n" + 
               "    REFERENCES `bookdb`.`main_category` (`id`)\r\n" + 
               "    ON DELETE RESTRICT\r\n" + 
               "    ON UPDATE RESTRICT)\r\n" + 
               "ENGINE = InnoDB;\r\n" + 
               "", "sub_category 테이블 생성");
         runSQL("CREATE TABLE IF NOT EXISTS `bookdb`.`book` (\r\n" + 
               "  `id` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + 
               "  `sub_category_id` INT(11) NOT NULL,\r\n" + 
               "  `name` VARCHAR(45) NOT NULL,\r\n" + 
               "  `image` MEDIUMBLOB NOT NULL,\r\n" + 
               "  `stock` INT(11) NOT NULL,\r\n" + 
               "  `price` INT(11) NOT NULL,\r\n" + 
               "  `author` VARCHAR(45) NOT NULL,\r\n" + 
               "  `intro` TEXT NOT NULL,\r\n" + 
               "  `numpages` INT(11) NOT NULL,\r\n" + 
               "  `isbn` VARCHAR(25) NOT NULL,\r\n" + 
               "  `created_by` DATE NOT NULL,\r\n" + 
               "  `hashtag` TEXT NULL,\r\n" + 
               "  PRIMARY KEY (`id`),\r\n" + 
               "  INDEX `fk_book_sid_idx` (`sub_category_id` ASC),\r\n" + 
               "  CONSTRAINT `fk_book_sid`\r\n" + 
               "    FOREIGN KEY (`sub_category_id`)\r\n" + 
               "    REFERENCES `bookdb`.`sub_category` (`id`)\r\n" + 
               "    ON DELETE RESTRICT\r\n" + 
               "    ON UPDATE RESTRICT)\r\n" + 
               "ENGINE = InnoDB;", "book 테이블 생성");
         runSQL("CREATE TABLE IF NOT EXISTS `bookdb`.`member` (\r\n" + 
               "  `id` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + 
               "  `login_id` VARCHAR(20) NOT NULL,\r\n" + 
               "  `login_pwd` VARCHAR(32) NOT NULL,\r\n" + 
               "  `birthdate` DATE NOT NULL,\r\n" + 
               "  `authority` VARCHAR(5) NOT NULL,\r\n" + 
               "  `phone` VARCHAR(15) NOT NULL,\r\n" + 
               "  `email` VARCHAR(50) NOT NULL,\r\n" + 
               "  PRIMARY KEY (`id`))\r\n" + 
               "ENGINE = InnoDB;", "member 테이블 생성");
         runSQL("CREATE TABLE IF NOT EXISTS `bookdb`.`order_log` (\r\n" + 
               "  `id` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + 
               "  `book_id` INT(11) NOT NULL,\r\n" + 
               "  `member_id` INT(11) NOT NULL,\r\n" + 
               "  `quantity` INT(11) NOT NULL,\r\n" + 
               "  `order_time` DATETIME NOT NULL,\r\n" + 
               "  PRIMARY KEY (`id`),\r\n" + 
               "  INDEX `fk_or_bid_idx` (`book_id` ASC),\r\n" + 
               "  INDEX `fk_or_mid_idx` (`member_id` ASC),\r\n" + 
               "  CONSTRAINT `fk_or_bid`\r\n" + 
               "    FOREIGN KEY (`book_id`)\r\n" + 
               "    REFERENCES `bookdb`.`book` (`id`)\r\n" + 
               "    ON DELETE RESTRICT\r\n" + 
               "    ON UPDATE RESTRICT,\r\n" + 
               "  CONSTRAINT `fk_or_mid`\r\n" + 
               "    FOREIGN KEY (`member_id`)\r\n" + 
               "    REFERENCES `bookdb`.`member` (`id`)\r\n" + 
               "    ON DELETE RESTRICT\r\n" + 
               "    ON UPDATE RESTRICT)\r\n" + 
               "ENGINE = InnoDB;", "order_log 테이블 생성");
         runSQL("CREATE TABLE IF NOT EXISTS `bookdb`.`survey_category` (\r\n" + 
               "  `id` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + 
               "  `group` VARCHAR(5) NOT NULL,\r\n" + 
               "  `description` VARCHAR(80) NOT NULL,\r\n" + 
               "  PRIMARY KEY (`id`),\r\n" + 
               "  INDEX `fk_gr_ind` (`group` ASC))\r\n" + 
               "ENGINE = InnoDB;", "survey_category 테이블 생성");
         runSQL("CREATE TABLE IF NOT EXISTS `bookdb`.`survey_results` (\r\n" + 
               "  `id` INT(11) NOT NULL AUTO_INCREMENT,\r\n" + 
               "  `member_id` INT(11) NOT NULL,\r\n" + 
               "  `survey_category_id` INT(11) NOT NULL,\r\n" + 
               "  `rating` INT(11) NOT NULL,\r\n" + 
               "  PRIMARY KEY (`id`),\r\n" + 
               "  INDEX `fk_sr_mid_idx` (`member_id` ASC),\r\n" + 
               "  INDEX `fk_sr_sid_idx` (`survey_category_id` ASC),\r\n" + 
               "  CONSTRAINT `fk_sr_mid`\r\n" + 
               "    FOREIGN KEY (`member_id`)\r\n" + 
               "    REFERENCES `bookdb`.`member` (`id`)\r\n" + 
               "    ON DELETE RESTRICT\r\n" + 
               "    ON UPDATE RESTRICT,\r\n" + 
               "  CONSTRAINT `fk_sr_sid`\r\n" + 
               "    FOREIGN KEY (`survey_category_id`)\r\n" + 
               "    REFERENCES `bookdb`.`survey_category` (`id`)\r\n" + 
               "    ON DELETE RESTRICT\r\n" + 
               "    ON UPDATE RESTRICT)\r\n" + 
               "ENGINE = InnoDB;", "survey_results 테이블 생성");
         
         runSQL("drop user if exists 'user'@'localhost'", "DB user 제거");
         runSQL("create user 'user'@'localhost' identified by '1234'", "DB user 생성");
         runSQL("grant select, insert, delete, update on bookdb.* to 'user'@'localhost'", "DB user 권한 부여");
         
         insertUser();
         insertBook();
         
         insertByCSV(pstSurvey, "./지급자료/survey_category.csv", "설문 항목 데이터 생성");
         insertByCSV(pstorder, "./지급자료/order_log.csv", "주문 항목 데이터 생성");
         insertByCSV(pstSurveyResult, "./지급자료/survey_results.csv", "설문 결과 데이터 생성");
         
         insertLog("DB 초기화 성공", blueBoldAttr);
         
      } catch (Exception e) {
         // TODO: handle exception
         insertLog("DB 초기화 실패", redBoldAttr);
         try {
            stmt.executeUpdate("drop database if exists bookdb");
         } catch (Exception e2) {
            // TODO: handle exception
         }
         
      }
      
      new Thread(() ->  {
         try {
            for (int i = 5; i > 0; i--) {
               insertLog(i + "초후 프로그램이 종료됩니다.", blackAttr);
               Thread.sleep(1000);
            }
         } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
         }
         dispose();
      }).start();
   }
   
   public void runSQL(String sql, String text) throws Exception {
      try {
         stmt.executeUpdate(sql);
         insertLog(text + " 성공", blueAttr);
      } catch (Exception e) {
         // TODO: handle exception
         insertLog(text + " 실패", redAttr);
         throw e;
      }
   }
   
   public void insertByTxt(PreparedStatement pstmt, HashMap<String, Integer> map, List<String> data) {
      try {
         for (int i = 0; i < data.size(); i++) {
            var d = data.get(i).split("\t");
            pstmt.setObject(map.get(d[0]), d[1]);
         }
      } catch (Exception e) {
         // TODO: handle exception
         e.printStackTrace();
      }
   }
   
   public void insertByCSV(PreparedStatement pstmt, String path, String text) throws Exception {
      try {
         var data = Files.readAllLines(Paths.get(path));
         for (int i = 1; i < data.size(); i++) {
            var d = data.get(i).split(",");
            System.out.println(d[0]);
            for (int j = 0; j < d.length; j++) {
               pstmt.setObject(j + 1, d[j]);
            }
            pstmt.executeUpdate();
         }
         insertLog(text + " 성공", blueAttr);
      } catch (Exception e) {
         // TODO: handle exception
         insertLog(text + " 실패", redAttr);
         insertLog(e.getMessage() + "(지정된 파일을 찾을 수 없습니다)", redAttr);
         throw e;
      }
   }
   
   public void insertUser() throws Exception{
      try {
         for(var path : Files.list(Paths.get("./지급자료/member_list")).toArray(Path[]::new)) {
            insertByTxt(pstMember, memberMap, Files.readAllLines(path));
            pstMember.executeUpdate();
         }
         insertLog("회원 기본 데이터 생성 성공", blueAttr);
      } catch (Exception e) {
         // TODO: handle exception
         insertLog("회원 기본 데이터 생성 실패", blueAttr);
         insertLog(e.getMessage() + "(지정된 파일을 찾을 수 없습니다)", redAttr);
         throw e;
      }
   }
   
   public void insertBook() throws Exception {
      int Id = 1, subId = 1, bookId = 1;
      String main = "", sub = "", book = "";
      boolean isCheck = false;
      for(var path : Files.list(Paths.get("./지급자료/categories")).toArray(Path[]::new)) {
         main = path.getName(path.getNameCount() - 1).toString();
         insertLog("main_category " + main + " 추가 시작" , blueAttr);
         try {
            stmt.executeUpdate("insert into main_category values('" + Id + "', '" + main + "')");
            for(var subPath : Files.list(path).toArray(Path[]::new)) {
               sub = subPath.getName(subPath.getNameCount() - 1).toString();
               insertLog("ㄴ sub_category " + sub + " 추가 시작" , blueAttr);
               try {
                  stmt.executeUpdate("insert into sub_category values('" + subId + "', '" + Id + "', '" + sub + "')");
                  for(var bookPath : Files.list(subPath).toArray(Path[]::new)) {
                     book = bookPath.getName(bookPath.getNameCount() - 1).toString();
                     if(book.contains(".txt")) {                        
                        insertLog(" ㄴ " + book.replace(".txt", "") + "추가 시작", blueAttr);
                        try {
                           pstBook.setInt(1, bookId);
                           pstBook.setInt(2, subId);
                           pstBook.setString(3, book.replace(".txt", ""));
                           pstBook.setBinaryStream(4, new FileInputStream(bookPath.toString().replace(".txt", ".jpg")));
                           insertByTxt(pstBook, bookMap, Files.readAllLines(Paths.get(bookPath.toString())));
                           pstBook.executeUpdate();
                        } catch (Exception e) {
                           // TODO: handle exception
                           insertLog(" ㄴ " + book.replace(".txt", "") + " 추가 실패", redAttr);
                           if(isCheck == false) {
                              insertLog(e.getMessage() + " (지정된 파일을 찾을 수 없습니다)", redAttr);
                              isCheck = true;
                           }
                           throw e;
                        }
                        bookId++;
                     }
                  }
                  subId++;
               } catch (Exception e) {
                  // TODO: handle exception
                  insertLog("ㄴ sub_category " + sub + " 추가 실패", redAttr);
                  if(isCheck == false) {
                     insertLog(e.getMessage() + " (지정된 파일을 찾을 수 없습니다)", redAttr);
                     isCheck = true;
                  }
                  throw e;
               }
            }
            Id++;
         } catch (Exception e) {
            // TODO: handle exception
            insertLog("main_category " + main + " 추가 실패", redAttr);
            if(isCheck == false) {
               insertLog(e.getMessage() + " (지정된 파일을 찾을 수 없습니다)", redAttr);
               isCheck = true;
            }
            throw e;
         }
      }
         
   }
   
   public void insertLog(String text, SimpleAttributeSet attr) {
      SwingUtilities.invokeLater(() -> {
         try {
            log.getStyledDocument().insertString(log.getStyledDocument().getLength(), text + "\n", attr);
         } catch (Exception e) {
            // TODO: handle exception
         }
         log.setSelectionStart(log.getStyledDocument().getLength());
      }); 
   }
   
   public static void main(String[] args) {
      // TODO Auto-generated method stub
      new DBSetting();
   }

}