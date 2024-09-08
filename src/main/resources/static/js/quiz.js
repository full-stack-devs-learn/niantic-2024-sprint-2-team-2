let questionNumber = 1;
let score = 0;
let numberOfQuestions = 0;
let correctChoices;
let quizId;
let totalScore;

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

function getQuestionCount()
{
    const url = `/api/quizzes/${quizId}`;

    fetch(url).then(response => response.text())
              .then(data => { 
                numberOfQuestions = +data;
                correctChoices = Array(numberOfQuestions);
                loadQuestion();})
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
        navigateQuestions();
        displayPrevButton();
        isItSubmittable();
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
            correctChoices[questionNumber - 1] = 1;
        }
        else
        {
            correctChoices[questionNumber - 1] = 0;
        }
        
        loadQuestion();

    })
}

function displayPrevButton()
{
    const prevButton = document.getElementById("prev-btn");
    
    if(questionNumber > 1)
    {
        prevButton.classList.remove("hide");
    }
}

function navigateQuestions()
{
    const form = document.getElementById("quiz-form");

    form.addEventListener("submit", (event) => {
        const whichDirection = event.submitter.value;

        if(whichDirection == 'prev')
        {
            questionNumber--;
        }
        else if(whichDirection == 'next')
        {
            questionNumber++;
        }
    })
}

function isItSubmittable()
{
    if (questionNumber === numberOfQuestions)
    {
        const nextButton = document.getElementById("next-btn");
        nextButton.textContent = "Submit";

        nextButton.addEventListener("click", () => calculateScore());
    }
}

function calculateScore()
{
    totalScore = correctChoices.reduce(
        (accum, curr) => accum + curr, 0
    );

    displayFinalScore();
}

function displayFinalScore()
{
    const quizContainer = document.getElementById("quiz-container");
    quizContainer.classList.add("hide");

    const scoreContainer = document.getElementById("score-container");
    scoreContainer.classList.remove("hide");

    const scoreDiv = document.getElementById("score");
    totalScore = totalScore/numberOfQuestions * 100;
    scoreDiv.innerHTML = "%" + Math.round(totalScore);
}
