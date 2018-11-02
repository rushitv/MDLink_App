package com.mdlink.util;

import java.util.Arrays;
import java.util.List;

public class Constants {
    public static final String APP_NAME = "MDLink";

    public static final String TYPE = "Type";
    public static final String PATIENT = "Patient";

    public static final String NAME = "Name";
    public static final String EMAIL = "Email";
    public static final String PHONE = "PhoneNumber";
    public static final String AGE = "Age";
    public static final String ROLE_ID ="RoleId";

    public static String BASE_URL = "http://api.themdlink.com/api/v1/";
    public static final String SEPARATOR = ",";
    public static int INVALID = -1;

    public static final String USER_ID = "UserId";
    public static final List<String> radiology_type = Arrays.asList(
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
}
