var currentIndexj = 0; //현재 이미지
function showNext() {
  var slideImgj = $(".slide_imgJ");
  var slidesj = $(".slide_imgJ").find(">div"); //슬라이드 이미지

  var slideCountj = slidesj.length; //
  var nextIndexj = (currentIndexj + 1) % slideCountj; //다음이미지
  slidesj.eq(currentIndexj).fadeOut(3000); //현재이미지를 사라지게~
  slidesj.eq(nextIndexj).fadeIn(1000); //다음이미지를 나타나게~
  //console.log("currentIndex : "+currentIndex);
  //console.log("nextIndex : "+nextIndex);
  currentIndexj = nextIndexj;
}

setInterval(showNext, 2000);
