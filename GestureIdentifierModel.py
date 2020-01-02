#Next Step is data collecting using Fossil smartwatch 
#Approx needed 10x features of data points = 30*4 classes for now  = 120
#Using svm

#sample file
from sklearn import datasets

#Load dataset
cancer = datasets.load_breast_cancer()

#Validate dataset is loaded
print("Features: ", cancer.feature_names)
print("Labels: ", cancer.target_names)

# print data(feature)shape
cancer.data.shape