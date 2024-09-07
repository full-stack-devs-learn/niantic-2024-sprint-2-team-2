document.addEventListener("DOMContentLoaded", () => {
    const addAnswer = document.getElementById("add-answer");
    const answerText = document.getElementById("answer-text");

    answerText.addEventListener("input", () => {
        addAnswer.classList.remove("was-validated");
    })

    addAnswer.addEventListener("submit", (event) => {
        if(!addAnswer.checkValidity())
        {
            event.preventDefault();
            addAnswer.classList.add("was-validated");
        }
    })
})