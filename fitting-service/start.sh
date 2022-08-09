mkdir ACGPN/Data_preprocessing/test_color
mkdir ACGPN/Data_preprocessing/test_edge
mkdir ACGPN/Data_preprocessing/test_img
mkdir ACGPN/Data_preprocessing/test_label
mkdir ACGPN/Data_preprocessing/test_mask
mkdir ACGPN/Data_preprocessing/test_pose
mkdir ACGPN/inputs/cloth
mkdir ACGPN/inputs/img

python manage.py migrate
python manage.py runserver 0.0.0.0:9002

