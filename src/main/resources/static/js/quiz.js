let quizId = 1;
let questionNumber = 1;
let score = 0;
let numberOfQuestions = 0;

document.addEventListener("DOMContentLoaded", () => {
    getQuestionCount();
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
    const url = `/quiz/${quizId}/${questionNumber}`;
    const quizContainer = document.getElementById("quiz-container");

    fetch(url).then(response => {
    if(response.status === 200)
    {
        return response.text();
    }
    throw new Error(response);
    }).then(data => {
        quizContainer.innerHTML = data;
        displayFinalScore();
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
    const url = `/api/quiz/${quizId}`;

    fetch(url).then(response => response.text())
              .then(data => { 
                numberOfQuestions = +data;
                startQuiz();})
}

function displayFinalScore()
{
    // when we get to final question
    // on the final question, replace NEXT with SUBMIT button
    if (questionNumber === numberOfQuestions)
    {
        const nextButton = document.getElementById("next-btn");
        nextButton.textContent = "Submit";
    }

    // hide/replace the question and answer fragment
    // then display final SCORE on a new fragment
}

// use fetch to get the Answer from server-side?
//    const url = `quiz/answer/${selection}`;
//
////    // use fetch to get the isCorrect value
////    fetch(url).then(response => {
////    if(response)
////    }).then(data => {
////        data.isCorrect
////    })

