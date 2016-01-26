#Chris Bawden (crbawden)
#1-23-16
#BDOSS Spring 2016
#FizzBuzz accepts integer n from command line and iterates from 1 to n.
    #Prints 'fizz if multiple of 2, prints 'buzz' if multiple of 3, prints 'fizzbuzz' if multiple of 2 and 3, prints n if else.

numbs = range (1,int(input("Please enter a number and press Enter)"))+1)

def fizzbuzz(numbs):
    for i in numbs:
        if i % 2 == 0 and i % 3 == 0:
            print ("fizzbuzz")
        elif i % 2 == 0:
            print ("fizz")
        elif i % 3 == 0:
            print ("buzz")
        else:
            print (i)

fizzbuzz(numbs)

input("FizzBuzz is complete, press enter to exit.")
