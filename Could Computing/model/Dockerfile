FROM python:3.12.0
LABEL maintainer="capstoneprojek@gmail.com"
LABEL description="Container for Flask app with Firestore integration"
WORKDIR /app

COPY requirements.txt .
RUN pip install --upgrade pip && \
    pip install -r requirements.txt

COPY key_firestore.json /app/key_firestore.json

COPY . .

RUN apt-get update && \
    apt-get install -y \
    libglib2.0-0 \
    libsm6 \
    libxext6 \
    libxrender1 && \
    pip install tensorflow==2.18.0

EXPOSE 8080
CMD ["waitress-serve", "--port=8080", "app:app"]
