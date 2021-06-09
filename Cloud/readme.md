

Online predictions using Flask API and Compute Engine

Endpoint 
Method POST

```bash
34.101.200.75:5000/predict/img/
```

Json request

```bash
{
    "data": "<base64 encode of images>"
}
```

#How to Replicate this Backend

# Prepare Google Cloud Platform Account

You can apply this application to the Google Cloud Platform using Compute Engine

**1. Open a Google Cloud Platform account. In the Google Cloud Console, on the project selector page, select or create a Google Cloud project**

[Go to project selector](https://console.cloud.google.com/projectselector2/home/dashboard) to select a project.

# Prepare Google Compute Engine

**2. Create Compute Engine

```bash
gcloud beta compute --project=b21-cap0467 instances create capstone-v2 --zone=asia-southeast2-a --machine-type=e2-medium --subnet=default --network-tier=PREMIUM --maintenance-policy=MIGRATE --service-account=755343573607-compute@developer.gserviceaccount.com --scopes=https://www.googleapis.com/auth/devstorage.read_only,https://www.googleapis.com/auth/logging.write,https://www.googleapis.com/auth/monitoring.write,https://www.googleapis.com/auth/servicecontrol,https://www.googleapis.com/auth/service.management.readonly,https://www.googleapis.com/auth/trace.append --tags=http-server --image=debian-10-buster-v20210512 --image-project=debian-cloud --boot-disk-size=10GB --boot-disk-type=pd-balanced --boot-disk-device-name=capstone-v2-1 --no-shielded-secure-boot --shielded-vtpm --shielded-integrity-monitoring --reservation-affinity=any
```

**3. Create a firewall rule allowing port 5000 for ingress

```bash
gcloud compute --project=b21-cap0467 firewall-rules create capstone-v2-http --direction=INGRESS --priority=1000 --network=default --action=ALLOW --rules=tcp:5000 --source-ranges=0.0.0.0/0 --target-tags=http-server
```

# Access the VM through ssh

# Configure VM

**4. Update system packages and install the required packages

```bash
sudo apt-get update
sudo apt-get install git
sudo apt-get install wget
```

**5. Download and install Miniconda

```bash
wget https://repo.anaconda.com/miniconda/Miniconda3-4.7.10-Linux-x86_64.sh
bash Miniconda3-4.7.10-Linux-x86_64.sh
```
**5.1. Export the full path of Miniconda to make it executable by the conda command

```bash
export PATH=/home/<your name here>/miniconda3/bin:$PATH
```

**5.2. Remove the installer

```bash
rm Miniconda3-4.7.10-Linux-x86_64.sh
```


**5.3. Confirm Miniconda installation

```bash
which conda
```

**5.4. Create and activate new environment

```bash
conda create -n capstone-v2 python=3.7
conda activate capstone-v2
```

## Note: You might need to close and re-open the SSH terminal for changes to take effect after running `conda init`

**6. Clone this repo

```bash
git clone https://github.com/DwiWahyuEffendi/Bangkit-B21-CAP0467
```

## Note: The required directory from the repo is only `Machine Learning`. You can delete the `Android` and `Cloud` directory.


**6.1. Go to the `deployment` directory and install the requirements

```bash
cd Bangkit-B21-CAP0467/Machine\ Learning/deployment/
pip install -r requirements.txt
```


**6.2. Run the app

```bash
python run.py
```
