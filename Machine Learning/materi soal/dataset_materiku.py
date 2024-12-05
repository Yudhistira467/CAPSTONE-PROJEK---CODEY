import pandas as pd

# Data sesuai format yang diminta
data = {
    "Introduction to Python": [
        "Welcome to your Python learning journey with Codey! Python is a popular, high-level programming language that’s easy to learn and very powerful. It’s used for web development, data science, machine learning, and much more. Don’t worry if you’re new to programming; Python’s syntax is simple and readable, which makes it a great starting point for beginners. You’ll be writing your own Python programs with ease! Let’s start by setting up your environment! To begin using Python, you'll need to install it on your computer. Download the latest version of Python from the official website. Don’t forget to check the box that says 'Add Python to PATH' during installation. If you’re unsure about installing, you can also use online platforms like Replit or Jupyter Notebooks to start coding without installing anything."
    ],
    "Operators": [
        "Arithmetic operators are used to perform basic math operations. For example, you can add (+), subtract (-), multiply (*), divide (/), and even calculate the modulus (remainder) using the percentage operator (%). These operators are great for building simple programs that solve real-world problems. Write a small program that asks for two numbers and calculates their sum. You can use the comparison operators to compare two values. For example, you can check if one number is greater than another with the '>' operator or check for equality with '=='. Comparison operators are often used in decision-making processes. Write a program to compare two numbers and tell which one is greater."
    ],
    "Data Types": [
        "Strings are sequences of characters enclosed in single or double quotes. Strings are useful for storing text, names, and sentences. You can do many cool things with strings, like combining them (concatenation), finding the length, and even slicing parts of them. Try printing your name in Python using a string! Integers are whole numbers, and floats are numbers with decimals. Python automatically recognizes the type of number you enter. You can perform arithmetic operations with both types of numbers. Try dividing two integers and see what happens!"
    ],
    "Data Structures": [
        "Lists are ordered collections of items in Python. You can store multiple data types in a list, such as numbers, strings, or even other lists! Lists are flexible, and you can easily add, remove, or change items. Access elements by their position, called an 'index'. Try creating a list of your favorite foods and print it out. Tuples are similar to lists, but they are immutable, which means once they are created, you cannot modify them. Tuples are great for storing data that should not change. You can access tuple elements by their index, just like lists. Try creating a tuple with a few items and print it."
    ],
    "Control Structures": [
        "If-else statements are used to make decisions in your program. For example, you can check if a user is old enough to watch a movie, and based on the result, allow or deny access. If-else statements follow a simple structure: If a condition is true, execute one block of code; if it’s false, execute another block. These are crucial for making your programs dynamic and responsive. Loops are used to repeat code multiple times. The two most common types of loops are the 'for' loop and the 'while' loop. For loops are great when you know exactly how many times you need to repeat something. While loops are useful when you want to repeat a block of code as long as a condition is true. Loops help you avoid writing the same code repeatedly, saving time and making your program efficient."
    ],
    "Function": [
    "A function in Python is a block of code designed to perform a specific task. Once defined, a function can be called multiple times, which helps eliminate code repetition and makes your code more modular and efficient. Functions are fundamental for building clean, readable, and maintainable code, whether you're working on simple scripts or large applications. By using functions, you can break down complex problems into smaller, manageable tasks, which enhances both the readability and reusability of your code."
    ],
    "Object-Oriented Programming (OOP)": [
        "In Python, everything is an object, and objects are created from classes. A class is like a blueprint for creating objects. Each object can have attributes (properties) and methods (functions). Think of a class as a cookie cutter, and the objects are the cookies you make from that cutter. You’ll be using classes and objects to create real-world models of the things you want to work with! Inheritance allows one class to inherit the attributes and methods of another class. This is incredibly powerful because it helps you avoid duplicating code and makes your programs more organized. For example, imagine you have a class called 'Animal' with common properties and methods, and you create specific classes like 'Dog' and 'Cat' that inherit from 'Animal'."
    ],
    "Lainnya": [
        "Modules and packages help you organize and reuse your code. A module is a single Python file containing functions and variables you can use in other programs. A package is a collection of modules grouped in a folder with an __init__.py file. With modules, you can import libraries like math or random to use their functions. Packages like NumPy and Pandas provide powerful tools for data science and analysis. Python comes with many built-in modules like os, sys, and datetime that you can import into your programs. These modules help you interact with the operating system, handle files, and even manipulate dates and times. Try importing the datetime module and printing today's date!"
    ]
}

# Membuat DataFrame
df = pd.DataFrame(data)

# Menyimpan DataFrame ke dalam file Excel
excel_file = "learning_material_topics_Codey.xlsx"
df.to_excel(excel_file, index=False)

excel_file