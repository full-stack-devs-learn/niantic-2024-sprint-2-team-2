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
    const url = `/api/quiz/${quizId}`;

    fetch(url).then(response => response.text())
              .then(data => { 
                numberOfQuestions = +data;
                loadQuestion();})
}

function displaySubmit()
{
    // when we get to final question
    // on the final question, replace NEXT with SUBMIT button
    if (questionNumber === numberOfQuestions)
    {
        const nextButton = document.getElementById("next-btn");
        nextButton.textContent = "Submit";

        nextButton.addEventListener("click", () => displayFinalScore());
    }

}

function displayFinalScore()
{
    // hide/replace the question and answer fragment
    // then display final SCORE on a new fragment
    const quizContainer = document.getElementById("quiz-container");
    quizContainer.classList.add("hide");

    const scoreContainer = document.getElementById("score-container");
    scoreContainer.classList.remove("hide");

    const scoreDiv = document.getElementById("score");
    score = score/numberOfQuestions * 100;
    scoreDiv.innerHTML = "%" + score;
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

