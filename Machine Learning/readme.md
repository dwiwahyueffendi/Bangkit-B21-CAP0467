#Bangkit-Capstone

The Bangkit Capstone Project machine learning model for Regular Devs (B21-CAP0467)

- **/deployment** contains run.py script to deploy the model using Flask that will be used in virtual machine of GCP's Compute Engine.
- **/exportedModel** contains the converted image classification model and its weights, also the model's notebook.
- **/savedModel** is the folder of the model saved using model.save()

#Model Description:

the machine learning model was made to classify images of traffic problems below:

0: Damaged road -> road with potholes

1: Flood -> flooded roads

2: Good -> roads with no problem at all

3: Traffic jam -> road with traffic jam

Even though the model reached 91% validation accuracy, in real-case it would perform worse. This is due to:

- The dataset is not large enough to obtain optimal feature extraction,
- The dataset went through **minimum** pre-processing, only using data augmentation to tackle the problem above,
- The input image from the application could be very varied in terms of: orientation, color, dimension, and quality.

#Dataset:

The dataset is a tailored dataset, gathered from existing kaggle datasets in the following links:

- [Road damage dataset](https://www.kaggle.com/prudhvignv/road-damage-classification-and-assessment)

- [Traffic jam dataset](https://www.kaggle.com/mashrukhzaman/banglanet)

- [Flood dataset](https://www.kaggle.com/saurabhshahane/roadway-flooding-image-dataset)

File of the tailored and finalized dataset: 

[Tailored dataset](https://drive.google.com/drive/folders/17pxXifl5BwvwuxBv7x_0X7WfOpoTDpNX?usp=sharing)


#Deployment:

The model is deployed using Flask through virtual machine from GCP's Compute Engine. The input for the deployment is an image encoded with base64, utf8, and converted into json to be sent to the flask.

The script to deploy Flask is named run.py consist of these functions:

- preprocess() to preprocess the input from the application by decoding, resizing, rescaling, and converting itu numpy array
- predicts() to get the prediction from the model and set the output into a dictionary of the necessary variables.
- predict_full_img() function to set the request endpoint and also define the flow of the image prediction response.
