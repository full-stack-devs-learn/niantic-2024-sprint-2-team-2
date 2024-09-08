document.addEventListener("DOMContentLoaded", () => {
    const answerInfo = document.getElementsByClassName("answer-info");
    const deleteBtn = document.getElementById("delete-btn");
    const deleteCancelBtn = document.getElementById("delete-cancel-btn");
    const cancelBtn = document.getElementById("cancel-btn");

    deleteBtn.addEventListener("click", () => {
        for(let i = 0; i < answerInfo.length; i++)
          {
              answerInfo[i].classList.add("hide");
          }
        deleteCancelBtn.classList.remove("hide");
    })

    cancelBtn.addEventListener("click", () => {
        for(let i = 0; i < answerInfo.length; i++)
          {
              answerInfo[i].classList.remove("hide");
          }
        deleteCancelBtn.classList.add("hide");
    })
})