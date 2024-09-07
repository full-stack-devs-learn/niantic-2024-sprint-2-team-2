let questionNumber = 1;
let score = 0;
let numberOfQuestions = 0;
let quizId;

document.addEventListener("DOMContentLoaded", () => {
    startQuiz();
});

function startQuiz()
{
    const startButton = document.getElementById("start");

    startButton.addEventListener("click", (event) => {
        quizId = event.target.value;
        startButton.classList.add("hide");
        getQuestionCount();
    }) 
}

function loadQuestion()
{
    const url = `/quizzes/${quizId}/${questionNumber}`;
    const quizContainer = document.getElementById("quiz-container");

    fetch(url).then(response => {
    if(response.status === 200)
    {
        return response.text();
    }
    throw new Error(response);
    }).then(data => {
        quizContainer.innerHTML = data;
        displaySubmit();
        evaluateAnswer();
    }).catch(error => {
        console.log(error)
    });
}

function evaluateAnswer()
{
    const form = document.getElementById("quiz-form");

    form.addEventListener("submit", (event) => {
        event.preventDefault();
        const selection = event.target.answer.value;

        if (selection == 'true')
        {
            score++;
        }

        questionNumber++;
        loadQuestion();

    })
}

function getQuestionCount()
{
    const url = `/api/quizzes/${quizId}`;

    fetch(url).then(response => response.text())
              .then(data => { 
                numberOfQuestions = +data;
                loadQuestion();})
}

function displaySubmit()
{
    if (questionNumber === numberOfQuestions)
    {
        const nextButton = document.getElementById("next-btn");
        nextButton.textContent = "Submit";

        nextButton.addEventListener("click", () => displayFinalScore());
    }
}

function displayFinalScore()
{
    const quizContainer = document.getElementById("quiz-container");
    quizContainer.classList.add("hide");

    const scoreContainer = document.getElementById("score-container");
    scoreContainer.classList.remove("hide");

    const scoreDiv = document.getElementById("score");
    score = score/numberOfQuestions * 100;
    scoreDiv.innerHTML = "%" + Math.round(score);
}
