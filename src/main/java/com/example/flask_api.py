from flask import Flask, request, jsonify
import joblib
import pandas as pd

# Load the trained model
model = joblib.load('fraud_detection_tuned_model.pkl')

# Initialize Flask app
app = Flask(__name__)

# A simple GET route to verify the server is up and running
@app.route('/')
def home():
    return "Flask server is running!"

# The main POST route for prediction
@app.route('/predict', methods=['POST'])
def predict():
    # Get data from the POST request
    data = request.get_json()

    # Prepare input data in the same format as your training data
    input_data = pd.DataFrame(data, index=[0])

    # Get prediction
    prediction = model.predict(input_data)
    response = {
        'prediction': int(prediction[0]),
        'message': 'Not Fraudulent Claim' if prediction[0] == 0 else 'Fraudulent Claim'
    }

    # Return the prediction as a response
    return jsonify(response)

if __name__ == '__main__':
    app.run(debug=True, port=5001)  # Ensure it's running on the correct port
