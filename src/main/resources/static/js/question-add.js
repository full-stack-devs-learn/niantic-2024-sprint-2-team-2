document.addEventListener("DOMContentLoaded", () => {
    const addQuestion = document.getElementById("add-question");
    const questionNumber = document.getElementById("question-number");
    const questionText = document.getElementById("question-text");

    questionNumber.addEventListener("input", () => {
        questionNumber.classList.remove("was-validated");
    })

    questionText.addEventListener("input", () => {
        questionText.classList.remove("was-validated");
    })

    addQuestion.addEventListener("submit", (event) => {
        if(!addQuestion.checkValidity())
        {
            event.preventDefault();
            addQuestion.classList.add("was-validated");
        }
    })
})