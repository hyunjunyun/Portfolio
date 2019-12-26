var currentIndex = 0; //현재 이미지
function showNext() {
  var slideImg = $(".slide_img");
  var slides = $(".slide_img").find(">div"); //슬라이드 이미지

  var slideCount = slides.length; //
  var nextIndex = (currentIndex + 1) % slideCount; //다음이미지
  slides.eq(currentIndex).fadeOut(1000); //현재이미지를 사라지게~
  slides.eq(nextIndex).fadeIn(1000); //다음이미지를 나타나게~
  //console.log("currentIndex : "+currentIndex);
  //console.log("nextIndex : "+nextIndex);
  currentIndex = nextIndex;
}

setInterval(showNext, 2000);
