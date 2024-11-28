import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import OneHotEncoder, LabelEncoder
from sklearn.linear_model import LogisticRegression
from sklearn.metrics import classification_report
from sklearn.pipeline import Pipeline
from sklearn.compose import ColumnTransformer
from sklearn.impute import SimpleImputer
from sklearn.preprocessing import StandardScaler

# Sample data for training
data = {
    'ClaimID': ['C001', 'C002', 'C003', 'C004', 'C005', 'C006'],
    'PolicyNumber': ['P12345', 'P67890', 'P11121', 'P33456', 'P78901', 'P54321'],
    'PolicyholderAge': [34, 29, 45, 50, 37, 62],
    'PolicyholderGender': ['Male', 'Female', 'Male', 'Female', 'Male', 'Female'],
    'PolicyType': ['Auto', 'Health', 'Home', 'Auto', 'Health', 'Home'],
    'IncidentType': ['Accident', 'Illness', 'Fire', 'Theft', 'Accident', 'Natural Disaster'],
    'IncidentDate': ['2024-10-10', '2024-09-15', '2024-07-20', '2024-08-05', '2024-06-12', '2024-03-10'],
    'ReportedDate': ['2024-10-12', '2024-09-17', '2024-07-25', '2024-08-06', '2024-06-15', '2024-03-12'],
    'Location': ['New York', 'Chicago', 'Los Angeles', 'Houston', 'Miami', 'Dallas'],
    'DamageSeverity': ['High', 'Medium', 'High', 'Low', 'Medium', 'High'],
    'ClaimAmount': [5000, 2000, 15000, 800, 3500, 12000],
    'FraudFlag': [0, 0, 1, 0, 0, 0],
    'SettlementTime': [15, 10, 30, 5, 20, 25],
    'SupportingDocsProvided': [1, 1, 0, 1, 1, 1]
}

df = pd.DataFrame(data)

# Feature Engineering
# Convert date columns to datetime
df['IncidentDate'] = pd.to_datetime(df['IncidentDate'])
df['ReportedDate'] = pd.to_datetime(df['ReportedDate'])

# Calculate the time to report the claim (days between IncidentDate and ReportedDate)
df['TimeToReport'] = (df['ReportedDate'] - df['IncidentDate']).dt.days

# Select Features and Target Variable
X = df[['PolicyholderAge', 'PolicyholderGender', 'PolicyType', 'IncidentType', 'DamageSeverity', 'ClaimAmount', 'SettlementTime', 'SupportingDocsProvided', 'TimeToReport']]
y = df['FraudFlag']

# One-hot encoding for categorical variables
categorical_features = ['PolicyholderGender', 'PolicyType', 'IncidentType', 'DamageSeverity']
numeric_features = ['PolicyholderAge', 'ClaimAmount', 'SettlementTime', 'SupportingDocsProvided', 'TimeToReport']

# Preprocessing Pipeline
preprocessor = ColumnTransformer(
    transformers=[
        ('num', StandardScaler(), numeric_features),
        ('cat', Pipeline(steps=[
            ('imputer', SimpleImputer(strategy='most_frequent')),
            ('encoder', OneHotEncoder(drop='first'))]), categorical_features)
    ])

# Split the dataset into training and testing sets
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.3, random_state=42)

# Create and train the logistic regression model within a pipeline
model = Pipeline(steps=[
    ('preprocessor', preprocessor),
    ('classifier', LogisticRegression())
])

# Train the model
model.fit(X_train, y_train)

# Predict on the test set
y_pred = model.predict(X_test)

# Evaluate the model
print(classification_report(y_test, y_pred))

