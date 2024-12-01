# import pandas as pd 
# from sklearn.model_selection import train_test_split, GridSearchCV
# from sklearn.linear_model import LogisticRegression
# from sklearn.metrics import classification_report
# from sklearn.preprocessing import StandardScaler
# from sklearn.impute import SimpleImputer
# from sklearn.pipeline import Pipeline
# from sklearn.compose import ColumnTransformer
# from sklearn.preprocessing import OneHotEncoder
# import joblib

# df = pd.read_excel('claims_data.xlsx', sheet_name='ClaimsData',header = 1)

# # Feature Engineering 
# df['IncidentDate'] = pd.to_datetime(df['IncidentDate'])
# df['ReportedDate'] = pd.to_datetime(df['ReportedDate'])
# df['TimeToReport'] = (df['ReportedDate'] - df['IncidentDate'])

# # Select Features and Target Variable
# X = df[['PolicyholderAge', 'PolicyholderGender', 'PolicyType', 'IncidentType', 'DamageSeverity',
#         'ClaimAmount', 'SettlementTime', 'SupportingDocsProvided', 'TimeToReport']]

# y = df['FraudFlag']

# # processing pipeline: in order to handle the categorical and numerical features 
# categorical_features = ['PolicyholderGender', 'PolicyType', 'IncidentType', 'DamageSeverity']
# numeric_features = ['PolicyholderAge', 'ClaimAmount', 'SettlementTime', 'SupportingDocsProvied', 'TimeToReport']

# preprocessor = ColumnTransformer(
#     transformers=[
#         ('num', StandardScaler(), numeric_features),
#         ('cat', Pipeline(steps=[
#             ('imputer', SimpleImputer(strategy='most_frequent')),
#             ('encoder', OneHotEncoder(drop='first', handle_unknown='ignore'))]), categorical_features)
#     ])
# # split dataset into training and testing sets 
# X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.3, random_state=42)

# # Create and train the logistic regression model wihin within a pipeline 
# model = Pipeline(steps=[
#     ('preprocessor', preprocesor),
#     ('classifier', LogisticRegression(max_iter=1000, class_weight='balanced'))
# ])

# # fit the model with training data 
# model.fit(X_train, y_train)

# #Evaluate the model 
# y_pred = model.predict(X_test)
# print(classification_report(y_test, y_pred))

# # Determine grid for Logistic Regression for fine-tuning 
# param_grid = {
#     'classifier__C' :[0.1,1,10], #Regularization strength
#     'classifier__solver':['libliner','saga'], #solver options
#     'classifier__max_iter': [1000], # increase iterations for convergence
#     'classifier__class_weight': ['balanced', None] # test class weights
# }

# # create GridSerarchCV 
# grid_search = GridSearchCV(model, param_grid, cv=5, n_jobs=-1, verbose=1)

# # fit grid search to the training data
# grid_search.fit(X_train, y_train)

# #print best parameters found 
# print("Best parameters:",grid_search.best_params_)

# # Best model after tuning
# best_model = grid_search.best_estimator_

# # save the fine-tuned model 
# joblib.dump(best_model, 'fraud_detection.pkl')




















