# CargoLink Android Application

## Overview
CargoLink is an Android application designed to streamline the process of managing and tracking deliveries for both senders and drivers. The application offers an intuitive user interface with features tailored to the specific needs of each user role.

Utilizing Firebase Realtime Database and Firebase Authentication, the app ensures secure and efficient data management and user authentication. Senders can create orders, view offers from drivers, and track the status of their deliveries. Drivers, on the other hand, can view available orders, make offers, and update the status of deliveries they are assigned to.

## Features

- **Sender:**
  - Create new delivery orders
  - View offers from drivers
  - Track the status of delivery orders
  - Receive notifications for order updates
  - Leave a review for driver
  
- **Driver:**
  - View available delivery orders
  - Make offers on delivery orders
  - Update the status of assigned deliveries
  - Receive notifications for accepted offers
  
## Installation
### Clone the Repository
```bash
git clone https://github.com/rozetadavchevska/CargoLink.git
```

### Open in Android Studio
Navigate to the project's root directory:
```bash
cd CargoLink
```

### Build the Application
```bash
#For Linux/macOS
./gradlew build
```
```bash
#For Windows
gradlew build
```

### Run the Application
After a successful build, run the application on your emulator or physical device. 
