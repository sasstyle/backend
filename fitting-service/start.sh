gdown --id 1rbSTGKAE-MTxBYHd-51l2hMOQPT_7EPy -O ACGPN/U_2_Net/saved_models/u2netp/u2netp.pth
gdown --id 1ao1ovG1Qtx4b7EoskHXmi2E9rp5CHLcZ -O ACGPN/U_2_Net/saved_models/saved_models/u2net/u2net.pth

python manage.py migrate
python manage.py runserver 0.0.0.0:9002

