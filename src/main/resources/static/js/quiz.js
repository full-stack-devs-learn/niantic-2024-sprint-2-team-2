let quizId = 1;
let questionId = 1;

document.addEventListener("DOMContentLoaded", () => {
    startQuiz();
});


function startQuiz()
{
    const startButton = document.getElementById("start");

    startButton.addEventListener("click", () => {
        startButton.classList.add("hide");
        loadQuestion();
    })
}

function loadQuestion()
{
    const url = `/quiz/${quizId}/${questionId}`;
    const quizContainer = document.getElementById("quiz-container");

    fetch(url).then(response => {
    if(response.status === 200)
    {
        return response.text();
    }
    throw new Error(response);
    }).then(data => {
        quizContainer.innerHTML = data;
        calculateScore();
    }).catch(error => {
        console.log(error)
    });
}

function calculateScore() {
    let score = 0;
    const form = document.getElementById("quiz-form");

//    let selection = 0;

    // when submit button is clicked

    form.addEventListener("submit", (event) => {
        event.preventDefault();
        const selection = event.target.answers.value;

        if (selection) {
            score++;
        }

        questionId++;
        loadQuestion();
    })

    // use fetch to get the Answer
//    const url = `quiz/answer/${selection}`;
//
////    // use fetch to get the isCorrect value
////    fetch(url).then(response => {
////    if(response)
////    }).then(data => {
////        data.isCorrect
////    })

}