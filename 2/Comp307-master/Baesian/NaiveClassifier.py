'''
Created on 19/05/2017

@author: jamesbadi
'''

trainingFile = open("spamLabelled.dat")
mailList = [[int(strInt) for strInt in mail.split()] for mail in trainingFile]

spam = [1 for i in range(13)]
notSpam = [1 for i in range(13)]

for mail in mailList:
    if mail[12] == 1:
        for i in range(len(mail)):
            spam[i] = spam[i] + mail[i]
    else:
        for i in range(len(mail) -1):
            notSpam[i] = notSpam[i] + mail[i]
        notSpam[12] = notSpam[12] + 1

print(spam)
print(notSpam)

total = spam[12] + notSpam[12]
# to account for initialising false counts with 0
featTotal = total + 2 
featSpamTotal = spam[12] + 1
featNotSpamTotal = notSpam[12] + 1
pSpam = spam[12] / total
pNotSpam = notSpam[12] / total

testFile = open("spamUnlabelled.dat")
testList = [[int(strInt) for strInt in mail.split()] for mail in testFile]

spamFolder = []
inbox = []

for mail in testList:
    likelySpam = 1.0
    likelyNotSpam = 1.0
    evidence = 1.0
    for i in range(len(mail)):
        trueMod = (0 + mail[i]) # 0 if false
        falseMod = (1 - mail[i]) # 0 if true
        featEvTrue = trueMod * (spam[i] + notSpam[i]) / featTotal  
        featEvFalse = falseMod * (featTotal - spam[i] - notSpam[i]) / featTotal 
        evidence = evidence * (featEvTrue + featEvFalse)
        featSpamTrue = trueMod * (spam[i] / featSpamTotal)
        featSpamFalse = falseMod * ((featSpamTotal - spam[i])/ featSpamTotal)
        featNotSpamTrue = trueMod * (notSpam[i] / featNotSpamTotal)
        featNotSpamFalse = falseMod * ((featNotSpamTotal - notSpam[i])/ featNotSpamTotal)
        likelySpam = likelySpam * (featSpamTrue + featSpamFalse)
        likelyNotSpam = likelyNotSpam * (featNotSpamTrue + featNotSpamFalse)
    posteriorSpam = (likelySpam * pSpam) / evidence
    posteriorNotSpam = (likelyNotSpam * pNotSpam) / evidence
    if posteriorSpam > posteriorNotSpam:
        spamFolder.append((mail, posteriorSpam))
    else :
        inbox.append((mail, posteriorNotSpam))

print("Test Set Spam")
for spam in spamFolder:
    print(spam)
print()
print("Test Set Not Spam")
for legit in inbox:
    print(legit)
        
          
