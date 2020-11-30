

###############################
#Average of (X) Numbers
#50 - STORES VALUE OF 1 (to increment total of numbers that have been added)
#51 - STARTS AT 0, HOLDS THE TOTAL VALUE OF ALL THE NUMBERS COMBINED
#52 - STARTS AT 0, HOLDS THE AMOUNT OF NUMBERS THAT HAVE BEEN ADDED
#53 - HOLDS THE QUOTIENT (answer) (TOTAL VALUE / TOTAL AMOUNT OF NUMBERS)
#54 - TEMPORARILY HOLDS USER INPUT
###############################

#Initialize increment variable amount
# 1) Set value of GPREG to 1
ILOAD 1

# 2) Store value of GRPEG (1) to MEMORY[50]
STOR 50

#Initialize total value of numbers amount and total amount of numbers amount
# 3) Set value of GPREG to 0
ILOAD 0

# 4) Store value of GPREG (0) to MEMORY[51] (Total Value)
STOR 51

# 5) Store value of GRPEG (0) to  MEMORY[52] (Total amount of numbers)
STOR 52

#Start taking user input, each number will be stored @ 54 (Temporarily)


# 6) Get user input + set to MEMORY[54]
READ 54

# 7) Set value of GPREG to MEMORY[54]
LOAD 54

# 8) IF GPREG IS EQUAL TO ZERO, BRANCH HERE (could be an error here)
BZ 14

#If the answer was not zero, this is where the code will continue (user still entering input)

#Add the value of the total value of numbers added to GPREG
#(which currently holds the recently entered number)
# 9) Add MEMORY[51] to GPREG (user input)
ADD 51

# 10) Store the sum of memory + GPREG back to MEMORY [51]
STOR 51

# Increment the total amount of numbers

# 11) Load MEMORY[52] (the total numbers that have been added) to GRPEG
LOAD 52

# 12) Add value of MEMORY[50] (staticlly set to 1) to GRPEG
ADD 50

# 13) Store the value of GPREG to MEMORY[52] (total numbers added)
STOR 52

#The number was confirmed to not be zero above, therefore the user wants to input another number
# 14) Unconditional loop to branch back to top to get next number (needs to go back to 6) (could be error)
BR 5


#GPREG IS EQUAL TO ZERO
#If the number 0 has been added, the user is done entering input
# 15) Load total value of all numbers to GRPEG - which is MEMORY[51]

LOAD 51

# 16) Divide GPREG (dividend) (total value) by MEMORY[52] (total numbers that have been added)
DIV 52


# 17) Store GPREG, which is the quotient to MEMORY[53] (the answer)
STOR 53

# 18) Write the answer to the screen MEMORY[53]
WRITE 53

# 19) Stop program
HALT 99