from decimal import *

def loadData(fname,ftype):
    with open(fname,'r') as fp:
        temp_rows=[]
        for line in fp:
            line.strip("\n")
            if ftype=="train":
                c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,c11,c12,cla=line.split()
                row=[int(c1),int(c2),int(c3),int(c4),int(c5),int(c6),int(c7),int(c8),int(c9),int(c10),int(c11),int(c12),int(cla)]
            else:
                c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,c11,c12=line.split()
                row=[int(c1),int(c2),int(c3),int(c4),int(c5),int(c6),int(c7),int(c8),int(c9),int(c10),int(c11),int(c12)]
            
            temp_rows.append(row)
        return temp_rows

def generateTable(dataSet):
    totalList=[]
    List_1=[]
    List_0=[]
    count_class=[]
    for L in dataSet:
        table_1 = [[0, 0] for _ in range(12)]
        table_0 = [[0, 0] for _ in range(12)]
       
        if L[12]==1:
            index=0
            for v in L[:12]:
                table_1[index][v] +=1
                index+=1
            # print table_1
            List_1.append(table_1)
        else:
            index=0
            for v in L[:12]:
                table_0[index][v]+=1
                index+=1
            # print table_0
            List_0.append(table_0)
    
    count_class.append(len(List_1))
    count_class.append(len(List_0))
    fTable=countFre(List_0)
    tTable=countFre(List_1)
    totalList.append(tTable)
    totalList.append(fTable)
    totalList.append(count_class)
    return totalList

def findDenominator(tempList):
    temp=[]
    for i in xrange(0,len(tempList),2):
        value = tempList[i]+tempList[i+1]
        temp.append(value)
        temp.append(value)
    return temp
    
    

def countFre(tempList):
    temp=[]
    for secLi in tempList:
        temp1=[]
        for thirLi in secLi:
            for value in thirLi:
                temp1.append(value)
        temp.append(temp1)
    result=[]
    for i in range(len(temp[0])):
        sum=0
        for j in range(len(temp)):
            sum+=temp[j][i]
        if sum==0:
            sum=1
        result.append(sum)
    return result

def getProb(tempList1,tempList2):
    result=[]
    for v,t in zip(tempList1,tempList2):
        proba = round(Decimal(v)/Decimal(t),2)
        result.append(proba)
    return result

def predictTest(testdata,probList):
    result=[]
    for secL in testdata:
        subList=[]
        for i in range(len(secL)):
            subList.append(probList[i*2+secL[i]])
        result.append(subList)
    return result
def predictHelper(testdata,trueProb,falseProb):
    testTrue_Pro=predictTest(testdata,trueProb)
    testFalse_Pro= predictTest(testdata,falseProb)
    # print testTrue_Pro
    # print testFalse_Pro
    numeraSpam=calNumerator(testTrue_Pro,True)
    
    numeraNonSpam= calNumerator(testFalse_Pro,False)
    probaList=calProbability(numeraSpam,numeraNonSpam)
    predicClass_List=predictClass(probaList[0],probaList[1]) 
    # print numeraSpam
    # print numeraNonSpam
    print 'spam probability:'
    print probaList[0]
    print 'nonspam probability:'
    print probaList[1]
    print 'predict class for each instance of test dataset:'
    print predicClass_List
def predictClass(trueList,falseList):
    result=[]
    for t,f in zip(trueList,falseList):
        if t>f:
            result.append(1)
        else:
            result.append(0)
    return result

def calNumerator(tempList,isSpam):
    result=[]
    for secL in tempList:
        if isSpam:
            multipe=51.0/200.0
            # print multipe
        else:
            multipe=149.0/200.0
        for i in range(len(secL)):
            multipe*=secL[i]
        result.append(multipe)
    return result
def calProbability(spamList, nonSpamList):
    spam_prob_List=[]
    nonSpam_prob_List=[]
    result=[]
    for t,f in zip(spamList,nonSpamList):
        denominator = 1.0/(t+f)
        spam_prob_List.append(round(t*denominator,4))
        nonSpam_prob_List.append(round(f*denominator,4))
    result.append(spam_prob_List)
    result.append(nonSpam_prob_List)
    return result




trainSet=loadData("spamLabelled.dat","train")
# trainSet=loadData("tempTrain.txt","train")
testSet=loadData("spamUnlabelled.dat","test")
finalRsult=generateTable(trainSet)
tDenominator=findDenominator(finalRsult[0])
fDenominator=findDenominator(finalRsult[1])
probTrue= getProb(finalRsult[0],tDenominator)
probFalse= getProb(finalRsult[1],fDenominator)
predictHelper(testSet,probTrue,probFalse)


# print probTrue
# print probFalse
# print testSet
# print "true table: ",finalRsult[0]
# print "true deno: ", tDenominator
# print "false table:",finalRsult[1]
# print 'false deno:',fDenominator
# print "number of each class",finalRsult[2]


