package com.mdlinkhealth.util;

import java.util.Arrays;
import java.util.List;

public class Constants {
    public static final String APP_NAME = "MDLink Health";

    public static final String TYPE = "Type";
    public static final String PATIENT = "Patient";

    public static final String NAME = "Name";
    public static final String EMAIL = "Email";
    public static final String PHONE = "PhoneNumber";
    public static final String AGE = "Age";
    public static final String ROLE_ID ="RoleId";
    public static final String BIRTH_DATE = "BirthDate";
    public static final String ADDRESS = "Address";
    public static final String LOCATION = "Location";
    public static final String USER_NAME = "UserName";
    public static final String URGENTCARE = "Urgent Care";
    public static final String LABEL_THERAPY = "Therapy";
    public static final String LABEL_DEPRESSION = "Depression And Mood";
    public static final String LABEL_ADDICTION = "Addiction";
    public static final String LABS_AND_SCREEN = "Labs And Screening";
    public static final String MEDICAL_ADVICE = "Medical Advice";
    public static final String APPOINTMENT_ID = "AppointmentId";
    public static final String DOCTOR_NAME = "DoctorName";
    public static final String PATIENT_ID = "PatientId";
    public static final String DOCTOR_PROFILE = "DoctorProfile";
    public static final String APPO_ROOM = "appo_room_";
    public static final String PATIENT_FILE = "PatientFile";
    public static final String JOIN = "Join";
    public static final String APPROVE = "Approve";
    public static final String CANCEL = "Cancel";
    public static final String DUMMY_TRANSACTIONID = "MDLINK100";
    public static final String DUMMY_TRANSACTIONSTAUS = "MDLINK_100_PAID";
    public static final String DUMMY_TRANSACTIONRESPONSE = "MDLINK_100_RESPONSE";

    public static String BASE_URL = "http://api.themdlink.com/api/v1/";
    public static final String SEPARATOR = ",";
    public static int INVALID = -1;

    public static final String USER_ID = "UserId";

    public static final List<String> listLabList = Arrays.asList(
            "ANA",
            "PTT (Partial Thromboplastin Time)",
            "Glycohemoglobin (Hemoglobin A1C)",
            "Acid Phos",
            "CBC (Complete Blood Count)",
            "CMP (Comprehensive Metabolic Panel)",
            "ESR (Sedimentation Rate)",
            "Flu (Influenza A and B Screen)",
            "Glucose Level",
            "hCG",
            "HIV Antibody (HIV 1/2 Ag/Ab 4th Generation with Reflex)",
            "Lipid Panel (or Lipid Profile)",
            "Liver Function Panel (LFT)",
            "Calcium",
            "Microalbumin, Urine",
            "Mono",
            "Rheumatoid Factor",
            "PSA (Prostate Specific Antigen)",
            "PT (Protime)",
            "Semen Analysis",
            "Stool Culture",
            "TSH, High Sensitivity (Thyroid Stimulating Hormone)",
            "Uric Acid",
            "Urinalysis",
            "Herpes 1 & 2",
            "H. Pylori",
            "Wound Swab",
            "FSH",
            "LH",
            "Prolactin",
            "Testosterone",
            "Oestradiol",
            "TFTs (Thyroid Function Tests)",
            "AFP",
            "CEA",
            "CA125",
            "Other"
    );

    public static final List<String> radiology_type = Arrays.asList(
            "None",
            "(X-RAY) HEAD - Sinuses",
            "(X-RAY) HEAD - Skull",
            "(X-RAY) HEAD - Mastoids",
            "(X-RAY) HEAD - Mandible",
            "(X-RAY) HEAD - Facial Bones",
            "(X-RAY) HEAD - Nasal Bones",
            "(X-RAY) HEAD - TMJ Bones",
            "(X-RAY) ABDOMEN - KUB",
            "(X-RAY) ABDOMEN - Esophagus",
            "(X-RAY) ABDOMEN - UGI",
            "(X-RAY) ABDOMEN - Sm Bowel",
            "(X-RAY) ABDOMEN - BE",
            "(X-RAY) ABDOMEN - IVP",
            "(X-RAY) ABDOMEN - OCG",
            "(X-RAY) SPINE - Cervical",
            "(X-RAY) SPINE - Thoracic",
            "(X-RAY) SPINE - Lumbar",
            "(X-RAY) SPINE - Sacrum",
            "(X-RAY) CHEST - PA",
            "(X-RAY) CHEST - PA &amp; LAT",
            "(X-RAY) CHEST - LAT",
            "(X-RAY) CHEST - 4 View Chest",
            "(X-RAY) CHEST - LAT Decubitus",
            "(X-RAY) CHEST - Sternum",
            "(X-RAY) CHEST - Clavicle",
            "(X-RAY) EXTREMITY - Shoulder",
            "(X-RAY) EXTREMITY - Humerus",
            "(X-RAY) EXTREMITY - Elbow",
            "(X-RAY) EXTREMITY - Forehand",
            "(X-RAY) EXTREMITY - Wrist",
            "(X-RAY) EXTREMITY - Hand",
            "(X-RAY) EXTREMITY - Pelvis",
            "(X-RAY) EXTREMITY - Hip",
            "(X-RAY) EXTREMITY - Femur",
            "(X-RAY) EXTREMITY - Knee",
            "(X-RAY) EXTREMITY - Leg",
            "(X-RAY) EXTREMITY - Ankle",
            "(X-RAY) EXTREMITY - Foot",
            "(X-RAY) EXTREMITY - Heel",
            "(ULTRASOUND) - Abdominal ",
            "(ULTRASOUND) - Breast",
            "(ULTRASOUND) - Renal",
            "(ULTRASOUND) - Gall Bladder",
            "(ULTRASOUND) - Pelvis",
            "(ULTRASOUND) - Aorta",
            "(ULTRASOUND) - Obstetrical",
            "(ULTRASOUND) - Endovaginal",
            "(ULTRASOUND) - Prostatic - Transrectal",
            "(ULTRASOUND) - Thyroid",
            "(ULTRASOUND) - Testicular",
            "(ULTRASOUND) - Baker Cyst",
            "(ULTRASOUND) - Deep Venous System",
            "(ULTRASOUND) - Upper Deep Venous System",
            "(ULTRASOUND) - Lower Deep Venous System",
            "(CT SCAN) HEAD - With Contrast",
            "(CT SCAN) HEAD - Without Contrast",
            "(CT SCAN) HEAD - With / without Contrast",
            "(CT SCAN) HEAD - Orbits",
            "(CT SCAN) HEAD - Sella Turcica",
            "(CT SCAN) HEAD - Paranasal Sinuses",
            "(CT SCAN) NECK - Larynx-hypopharynx",
            "(CT SCAN) NECK - Salivary and Parotid glands",
            "(CT SCAN) CHEST - Mediastinum",
            "(CT SCAN) CHEST - Lungs",
            "(CT SCAN) ABDOMEN - Liver, Spleen, Pancreas",
            "(CT SCAN) ABDOMEN - Kidneys, Adrenals",
            "(CT SCAN) ABDOMEN - Retroperitoneum",
            "(CT SCAN) ABDOMEN - Metastatic Survey",
            "(CT SCAN) Pelvis",
            "(CT SCAN) EXTREMITIES - Shoulders",
            "(CT SCAN) EXTREMITIES - Wrists",
            "(CT SCAN) EXTREMITIES - Hips",
            "(CT SCAN) EXTREMITIES - Knee or Ankle",
            "(CT SCAN) EXTREMITIES - Others",
            "(CT SCAN) SPINE - Cervical",
            "(CT SCAN) SPINE - Thoracic",
            "(CT SCAN) SPINE - Lumbosacral",
            "(MAMMOGRAM)",
            "(ECG)",
            "(ECHOCARDIOGRAM)"
            );

    public static final List<String> URGENT_LIST = Arrays.asList(
            "Allergies",
            "Back Pain",
            "Bronchitis and Pneumonia",
            "Cellulitis and Skin Infections",
            "Colds, Coughs, Congestion",
            "Conjunctivitis",
            "Headache / Migraine",
            "Influenza",
            "Rashes and Skin Conditions",
            "Sinus Infections",
            "Allergy & Asthma",
            "Obesity",
            "High Blood Pressure",
            "High Cholesterol",
            "Metabolic Syndrome",
            "Pre-Diabetes / Diabetes",
            "Stress Management",
            "Thyroid",
            "Urinary Tract Infections",
            "Vaginal and Yeast Infections",
            "Sexually Transmitted Diseases",
            "Vomiting and Diarrhea",
            "Sport Injuries",
            "Sprains and Bruises",
            "Erectile Dysfunction",
            "Acne",
            "Prescription Renewal"
    );

    public static final List<String> MEDICAL_ADVICE_LIST = Arrays.asList(
            "Am I experiencing a medical emergency?",
            "Can I take these medications together?",
            "Can you help me when my doctor is out of town?",
            "Do I need a specialist?",
            "Is this a side effect of a medication or another issue?",
            "Night and Weekend Medical questions",
            "Should I go to the ER or Urgent Care?",
            "What can I expect with my new medication?"
    );

    public static final List<String> LABS_AND_SCREENING = Arrays.asList(
            "STDs",
            "Anemia",
            "Cardiovascular",
            "Chronic disease",
            "Depression",
            "Diabetes",
            "Drug levels",
            "Fatigue",
            "Fertility",
            "Thyroid disease",
            "Vitamin deficiencies"
    );

    public static final List<String> ADDICTION = Arrays.asList(
            "Weight loss counseling",
            "Behavioral Pain Management",
            "Drug and Alcohol Dependence",
            "Food Addiction",
            "Medication Addiction",
            "Opioid Addiction",
            "Smoking Cessation"
    );
    public static final List<String> DEPRESSION_AND_MOOD = Arrays.asList(
            "Bipolar Disorder",
            "Chronic Depression",
            "Dysthymia",
            "Fatigue",
            "Major Depression",
            "Mood Swings",
            "Postpartum Depression"
    );
    public static final List<String> THERAPY = Arrays.asList(
            "Stress and Anxiety",
            "Trauma and Loss",
            "Anger Management",
            "Behavioral Therapy",
            "Cognitive Behavioral Therapy",
            "Dialectical Behavioral Therapy",
            "Emotional Focused Therapy",
            "Post Cardiac Event Counseling",
            "Psychodynamic Therapy"
    );

    public static final String EXTRA_CHANNEL = "com.twilio.chat.Channel";
    /** Key into an Intent's extras data that contains Channel SID. */
    public static final String EXTRA_CHANNEL_SID = "C_SID";

    public static final String CHANNEL_DEFAULT_NAME = "appo_room_";
    public static final String TWILIO_USER = "RushitV-19000";
    public static String FCM_REG_TOKEN = "FCM_REG_ID";
    public static final String DEVICE_TYPE = "android";
}
