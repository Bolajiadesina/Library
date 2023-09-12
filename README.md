Library Homework Assignment 
Instructions
Below is a programming assignment. You are allowed to use third-party libraries and tools in 
order for developing, building, and testing purposes (please note exclusions below). Please 
include an explanation of your design, assumptions as well as detailed instructions on how to run 
the application along with your code (no more than one side of A4). We don't require a front-end 
or a command-line interactive interface. We are most interested in how you design your classes 
and solve the problem at hand. 
The code is assessed for: 
• the design 
• object-oriented programming skills 
• production quality
• maintainability 
• extensibility 
• scalability (Will your design work with say 1 million books in the library)
Please compress only the source (including the build files) into a single zip. There should be no 
jarfiles or executables in the submitted zip file. 
The Problem
You have been tasked with building part of a simple library system that allows customers to 
borrow books, DVDs, VHSes, and CDs. Your code should provide the following functionality: 
• Borrow and return items - items are loaned out for a period of one week. 
• For example, a customer can borrow WarGames on 21st February and they will be expected to 
return it by 28th February. 
• Determine current inventory - this should show you the current items that are loanable. You 
should make allowances for multiple copies of the same item (i.e. there can be multiple copies of 
the same book/movie). 
• For example, if you choose to use the initial inventory, the current inventory should return the 
titles. 
• Determine overdue items. i.e. all items that should have been returned before today. 
• For example, if a book was due on 12th February and today is 15th February, that book should be 
flagged as overdue. 
• Determine the borrowed items for a user. 
• Determine if a book is available. 
Notes 
• No JDBC 
• No Hibernate or anything similar. 
• No distributed caches 
• Make it thread safe. 
• No ChatGPT 
In order to test this application, you may choose to import the following initial inventory (in CSV 
format) or create your own (make sure it's part of the submitted zip file): 
UniqueID,ItemID,Type,Title 
1,5,DVD,Pi 
2,6,VHS,Pi 
3,1,Book,The Art Of Computer Programming Volumes 1-6 
4,2,Book,The Pragmatic Programmer 
5,3,Book,Java Concurrency In Practice 
6,4,Book,Introduction to Algorithms 
7,5,DVD,Pi 
8,4,Book,Introduction to Algorithms 
9,6,VHS,WarGames 
10,7,VHS,Hackers 
11,4,Book,Introduction to Algorithms 
12,5,DVD,Pi 
Note: Two copies of Titanic DVD will have the same ItemID but different UniqueI
