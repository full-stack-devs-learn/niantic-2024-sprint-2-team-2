let questionId = 1;
let hasCorrect = false;

document.addEventListener("DOMContentLoaded", () => {
    getQuestionId();

})

function getQuestionId()
{
    questionId = document.getElementById("question-id").value;
    alert(questionId);

    checkForCorrectAnswer();
}

function checkForCorrectAnswer()
{
    // api returns a boolean
    const url = `/api/question/${questionId}`;

    fetch(url).then(response => response.text())
    .then(data => {
        if(data == "true")
        {
            hasCorrect = true;
        }})
    .then(allowAddCorrect())
    .catch(error => {
            console.error("Error fetching data:", error);
        });;
}

function allowAddCorrect()
{
    const checkbox = document.getElementById("correct-checkbox");
    const submitBtn = document.getElementById("submit-btn");
//    const invalidCorrect = document.getElementById("invalid-correct");
    const form = document.getElementById("add-answer");

    // display error if there is already a correct answer
    if(hasCorrect) // should not allow user to add answer as correct
    {
        event.preventDefault();
        form.addEventListener("input", () => {
            submitBtn.classList.add("was-validated");
        });
    }
    else
    {
        // allow add answer
        checkbox.addEventListener("input", () => {
            checkbox.classList.remove("was-validated");
        })

        submitBtn.addEventListener("submit", (event) => {
            if(!form.checkValidity())
            {
                event.preventDefault();
                submitBtn.classList.add("was-validated");
            }
        });
    }
}
