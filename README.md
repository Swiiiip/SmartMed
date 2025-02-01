# **SmartMed - MOM d'hôpitaux** 🏥💡

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white) ![ActiveMQ](https://img.shields.io/badge/ActiveMQ-FF6600?style=for-the-badge&logo=apache&logoColor=white) ![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white) ![JMS](https://img.shields.io/badge/JMS-007396?style=for-the-badge&logo=java&logoColor=white)

---

## **📖 SOMMAIRE**
1. [🎯 Contexte](#-contexte)
2. [📂 Structure du Code](#-structure-du-code)
3. [⚙️ Principes Utilisés](#%EF%B8%8F-principes-utilisés)
4. [🛠 Installation et Pré-requis](#-installation-et-pré-requis)
5. [🚀 Guide d'Exécution](#-guide-dexécution)
6. [🎭 Scénario de Test](#-scénario-de-test)

---

## **🎯 CONTEXTE**
### **🩺 SmartMed - Middleware Orienté Messages pour la Santé**
SmartMed est une solution de **MOM (Middleware Orienté Messages)** destinée aux **établissements de santé**.  
L’objectif est de faciliter la **communication entre les différents services hospitaliers** :  
🚨 Alertes d'urgence pour les patients \
👨‍⚕️ Assignation des tâches aux médecins \
🔬 Gestion des demandes et résultats de laboratoire

### **🔎 Pourquoi un MOM ?**
Les **hôpitaux** sont confrontés à des défis de **communication en temps réel** entre médecins, laboratoires et services d'urgence.  
💡 **SmartMed** utilise un système de **messagerie asynchrone** basé sur **ActiveMQ** pour une transmission fiable et instantanée des messages.

### **🛠 Utilisation typique**
1. **Un patient en danger** déclenche une **alerte d’urgence**.
2. Les **médecins et infirmiers** reçoivent cette alerte via un **Topic JMS** (publication/abonnement).
3. Un **médecin est assigné** au patient via une **file JMS** (point-à-point).
4. Une **demande d'analyse en laboratoire** est envoyée en **RPC (Request-Reply)**.
5. Le **laboratoire analyse** la demande et **retourne les résultats**.

---

## **📂 STRUCTURE DU CODE**
```
📂 SmartMed
│── 📂 src
│   ├── 📂 alerts (Gestion des alertes d’urgence)
│   │   ├── PatientAlertProducer.java
│   │   ├── AlertConsumer.java
│   ├── 📂 tasks (Assignation des médecins)
│   │   ├── DoctorAssignmentProducer.java
│   │   ├── DoctorConsumer.java
│   ├── 📂 lab (Demandes et résultats de tests)
│   │   ├── LabTestRequester.java
│   │   ├── LabTestResponder.java
│   ├── 📂 utils (Outils partagés)
│   │   ├── JMSConnectionFactory.java
│── 📄 pom.xml (Maven)
│── 📄 README.md
│── 📄 .gitignore
```
---

## **⚙️ PRINCIPES UTILISÉS**
### ✅ **Synchrone vs. Asynchrone**
- **Asynchrone** : Permet une **meilleure scalabilité** et évite le blocage des services médicaux.

### ✅ **Persistant vs. Transitoire**
- **Persistant** : Assure qu’aucun message critique (ex: alertes patient) ne soit perdu en cas de panne.
- **Transitoire** : Utilisé pour des messages non critiques (ex: confirmation de réception).

### ✅ **Unicast vs. Multicast**
- **Unicast (Point à Point)** : Assignation des médecins via une **file JMS**.
- **Multicast (Publication/Abonnement)** : Alertes d'urgence envoyées **à plusieurs médecins**.

### ✅ **Mode de consommation : "Push" vs. "Pull"**
- **Push** : Alertes **instantanées** vers les médecins.
- **Pull** : Les médecins **récupèrent** les dossiers patients à traiter selon leur disponibilité.

### ✅ **Patrons de communication**
- **Point à Point (Queue JMS)** → Assignation de tâches aux médecins.
- **Publication / Abonnement (Topic JMS)** → Diffusion des alertes d’urgence.
- **RPC (Request-Reply)** → Communication entre médecins et laboratoires.

### ✅ **Systèmes de routage des messages**
Utilisation de **Queues JMS et Topics JMS** pour une distribution efficace.

---

## **🛠 INSTALLATION ET PRÉ-REQUIS**
### **1️⃣ Installer Java JDK 21+**
- Vérifier la version Java installée :
  ```sh
  java -version
  ```

### **2️⃣ Installer ActiveMQ**
- Télécharger **ActiveMQ** depuis [Apache ActiveMQ](https://activemq.apache.org/components/classic/download/).
- Extraire et **naviguer dans le dossier**.

### **3️⃣ Démarrer le broker ActiveMQ**
- **Exécuter** ActiveMQ :
  ```sh
  ./activemq start
  ```
  (*Sous Windows : `activemq.bat start`*)

- **Vérifier que le broker est actif** sur :  
  📌 `http://localhost:8161/admin/`

> **Par défaut, les credentials sont :** \
> **Username** : admin \
> **Password** : admin

- **Arrêter ActiveMQ** :
  ```sh
  ./activemq stop
  ```

---

## **🚀 GUIDE D'EXÉCUTION**
### **1️⃣ Lancer ActiveMQ**
```sh
./activemq start
```

### **2️⃣ Lancer les consommateurs (listeners)**
🔵 **Alertes (médecins & infirmiers)**
```sh
java -cp target/SmartMed-1.0.jar alerts.AlertConsumer
```
🔵 **Médecins (tâches médicales)**
```sh
java -cp target/SmartMed-1.0.jar tasks.DoctorConsumer
```
🔵 **Laboratoire (réponses aux tests)**
```sh
java -cp target/SmartMed-1.0.jar lab.LabTestResponder
```

### **3️⃣ Lancer les producteurs (émission des messages)**
🟢 **Envoyer une alerte patient**
```sh
java -cp target/SmartMed-1.0.jar alerts.PatientAlertProducer
```
🟢 **Envoyer une assignation de médecin**
```sh
java -cp target/SmartMed-1.0.jar tasks.DoctorAssignmentProducer
```
🟢 **Demander un test laboratoire**
```sh
java -cp target/SmartMed-1.0.jar lab.LabTestRequester
```

---

## **🎭 SCÉNARIO DE TEST**
### **🔴 Situation Réelle : Alerte Patient et Prise en Charge**
1. **Un patient fait un arrêt cardiaque.** 🚨
   - Une alerte est envoyée aux médecins via **AlertProducer**.
   - **Tous les médecins abonnés** reçoivent l’alerte via **AlertConsumer**.

2. **Un médecin est assigné au patient.** 🏥
   - Une tâche est envoyée à un médecin disponible via **DoctorAssignmentProducer**.
   - Un médecin récupère la tâche via **DoctorConsumer**.

3. **Le médecin demande une analyse de sang au laboratoire.** 🧪
   - La demande est envoyée via **LabTestRequester**.
   - Le laboratoire traite la demande et répond via **LabTestResponder**.

---

> 👨‍💻 **Jade HATOUM**  
> 👨‍💻 **El Mahdi ESSETNI**  
> 🚀 **I2-APP LSI2** - *2025*