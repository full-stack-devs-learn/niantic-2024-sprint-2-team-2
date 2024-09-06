document.addEventListener("DOMContentLoaded", () => {
    const addQuiz = document.getElementById("add-quiz");
    const title = document.getElementByID("title");

    title.addEventListener("input", () => {
        addQuiz.classList.remove("was-validated");
    });

    addQuiz.addEventListener("submit", (event) => {

        if(!addQuiz.checkValidity())
        {
            event.preventDefault();
            addQuiz.classList.add("was-validated");
        }

    })

}