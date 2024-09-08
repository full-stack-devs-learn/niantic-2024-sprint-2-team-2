document.addEventListener("DOMContentLoaded", () => {
    const answerInfo = document.getElementsByClassName("answer-info");
    const deleteBtn = document.getElementById("delete-btn");
    const deleteCancelBtn = document.getElementById("delete-cancel-btn");
    const cancelBtn = document.getElementById("cancel-btn");

    deleteBtn.addEventListener("click", () => {
//        answerInfo.forEach((element) => element.classList.add("hide"));

        // Iterate through the retrieved elements and add the necessary class names.
          for(let i = 0; i < answerInfo.length; i++)
          {
              answerInfo[i].classList.add("hide");
          }
//        answerInfo.classList.add("hide");
//        array1.forEach((element) => console.log(element));
        deleteCancelBtn.classList.remove("hide");
    })

    cancelBtn.addEventListener("click", () => {
//        editDeleteBtn.classList.remove("hide");
        for(let i = 0; i < answerInfo.length; i++)
          {
              answerInfo[i].classList.remove("hide");
          }
        deleteCancelBtn.classList.add("hide");
    })
})