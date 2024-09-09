# Trivio Quizzlet App
A two-day pair programming sprint project! Trivio is a simple web application that allows users to take a quiz and create their own quizzes.

## Table of Contents
1. [Description](#description)
2. [Features](#features)
3. [Instructions](#instructions)
4. [Development process](#development-process)
5. [Challenges](#challenges)
6. [Retrospective](#retrospective)

## Description
Trivio is a simple web application that allows users to take a quiz and create their own quizzes. Built using SpringBoot and Thymeleaf, the application consists of a front-end interface for users to interact with the quiz and a back-end system for managing the quiz questions and answers. Built with Java, Spring Boot, Thymeleaf, HTML, CSS, JavaScript, and Bootstrap, this app offers an interactive and user-friendly way to create and enjoy trivia games. It's perfect for quiz enthusiasts looking to challenge themselves or share custom quizzes with others.

This is a two-day pair programming sprint project created by Eri and Chin.

## Features
### Take quizzes
Users can select from a range of trivia quizzes and test their knowledge.

### Create new quizzes
Add your own quizzes with your customized set of questions and answers.

### Modify quiz contents
Modify existing quizzes by adding or updating questions and answers.

### User-friendly Interface
The app features an intuitive interface using Bootstrap and CSS.

### Validation
Form validation ensures proper quiz creation and editing, including limiting correct answers to one per question.

## Instructions
### Setup
#### Prerequisites
Before you begin, ensure that you have the following installed on your system:
- JDK
- Maven
- Text editor or IDE such as [IntelliJ IDEA](https://www.jetbrains.com/idea/)

#### Clone this repository
```
git clone https://github.com/full-stack-devs-learn/niantic-2024-sprint-2-team-2.git
cd niantic-2024-sprint-2-team-2
```

#### Initialize the database
Run the `trivio-db.sql` database script in the `database` folder to create your database in your local MySQL connection.

#### Run and access the application
From your text editor or IDE, build and run the application. After that, you can go to your web browser and navigate to the following URL:
```
http://localhost:8080
```

#### Additional configuration
You can change the port on which the application runs by editing the `src/main/resources/application.properties` file. Feel free to explore the codebase and make any desired changes to suit your needs. If you have any questions or issues during the installation process, please feel free to open an issue or contact the project maintainers.

### How to use the website
The home page shows all of your live quizzes.

If you wish to add or edit an existing quiz, go to "Manage Quizzes". Here, you can see all live and un-live quizzes.

Questions can be added through each Quiz Details page.

Answers can be added from their respective questions.

## Development process
We used Trello's kanban board to manage our project progress. High priority tasks were given red labels. Below is a screenshot of our kanban board at an earlier stage in the project.

![trello-in-progress.png](/img/trello-in-progress.png)

When we started our project, we focused on building out the main purpose of our application: **to allow users to be able to take a quiz**. Our general approach was to **develop iteratively**: we broke up big features into smaller tasks and went through cycles of coding, testing, and refining.

### Diagrams
We diagrammed the database, models, and services so that we could better understand the relationship between groups of data, and reference at a glance.

**Database**
![trivio-database-diagram](/img/trivio-database-diagram.png)

**Models**
![trivio-dto-diagram](/img/trivio-dto-diagram.png)

**Services**
![trivio-dao-diagram](/img/trivio-dao-diagram.png)

## Challenges
### Challenge 1
Partial page loading

### Challenge 2

### Code that we're proud of
#### Eri
For me, the code that I'm particularly proud of is the loadQuestion() function in the `quiz.js` file. I was struggling to understand why our code wasn't executing at the correct timing, and then it finally clicked with me what `fetch()` actually does, what a 'promise' means, and what functions I should put in `then()`.

![trivio-eri-code](/img/trivio-eri-code.png)

#### Chin
The improvement that I was proud of is the code for the Answer Edit page. I wanted to make it easier for users to see which question they were editing the answer for, so I made sure to display the question text along with the existing answer data, rather than just showing the question ID.

In the GetMapping, I retrieve the current answer using answerDao and the related question text using questionDao. By passing both the answer and the question text to the view, users can see exactly which question answer they’re editing, making the process clearer.
The PostMapping handles form validation and updates the answer if no errors occur. If validation fails, I ensure the form reloads with the relevant answer and question information, maintaining a smooth user experience.
This code improves usability by ensuring users have all the context they need when editing answers, reducing confusion and enhancing the overall experience.

```java
@GetMapping("/answers/{answerId}/edit")
public String editAnswer(Model model, @PathVariable int answerId)
{
    var answer = answerDao.getAnswerById(answerId);

    if(answer == null)
    {
            return "404";
    }

    var questionId = answer.getQuestionId();
    var questionText = questionDao.getQuestionText(questionId);

    model.addAttribute("questionId", questionId);
    model.addAttribute("questionText", questionText);
    model.addAttribute("answer", answer);
    model.addAttribute("action", "edit");
    return "answer/add-edit";
}

@PostMapping("/answers/{answerId}/edit")
public String editAnswer(Model model, @Valid @ModelAttribute("answer") Answer answer, BindingResult result, @PathVariable int answerId)
{
    if(result.hasErrors())
    {
        var selectedAnswer = answerDao.getAnswerById(answerId);
        var questionId = selectedAnswer.getQuestionId();
        var questionText = questionDao.getQuestionText(questionId);

        model.addAttribute("selectedAnswer", selectedAnswer);
        model.addAttribute("questionId", questionId);
        model.addAttribute("questionText", questionText);
        model.addAttribute("isInvalid", true);
        model.addAttribute("action", "edit");
        return "answer/add-edit";
    }
    answerDao.updateAnswer(answer);
    return "redirect:/quizzes";
}
```

## Retrospective
This project allowed us to further understand how Java, JavaScript, SpringBoot, and Thymeleaf work together to build a full web application.

### Things we would do differently
- Plan out the hierarchy of URLs so that we can structure them and have proper grouping within sections of the website.

### Things we would do the same
Clear Documentation
We made sure to maintain our documentation throughout the project, and it definitely paid off. Using diagrams and charts helped us map out the flow and structure of the app, making complex ideas easier to grasp. Our Trello Kanban board kept us organized, on track, and ensured we were prioritizing the right tasks at the right time.

Development Practice
Pair programming turned out to be a real win for us. Having two minds on the same problem meant we could catch issues early and work through challenges faster. Here’s what made it work:
- Quick Feedback: We didn’t have to wait for code reviews—we were able to discuss things on the spot and make improvements right away.
- Skill Sharing: Working closely together helped us learn from each other and level up our skills as we went.
- Better Productivity: Tackling the project as a team helped us move through tricky parts faster than if we were coding solo.

### Future Ideas
- Allow user to edit quizzes, questions, and answers from the same details page without redirecting to a new "adding" screen.
- Validate answers on server-side instead of client-side so that answers are hidden when inspecting the HTML.
- When adding new questions, the form should indicate which question numbers have already been used.
- Implement more custom CSS styling