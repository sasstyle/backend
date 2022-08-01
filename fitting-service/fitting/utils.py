import os 
import time 
from PIL import Image
from urllib.request import urlopen

from ACGPN.predict_pose import generate_pose_keypoints
from ACGPN.U_2_Net import u2net_load
from ACGPN.U_2_Net import u2net_run

def virtual_try_on(profileUrl, images, profile_name, cloth_name): 
        profile = Image.open(urlopen(profileUrl))
        cloth = Image.open(urlopen(images))

        profile_path = 'ACGPN/inputs/img/' + profile_name
        cloth_path = 'ACGPN/inputs/cloth/' + cloth_name

        profile.save(profile_path)
        cloth.save(cloth_path)

        u2net = u2net_load.model(model_name = 'u2netp')

        sorted(os.listdir('ACGPN/inputs/cloth'))
        cloth = Image.open(cloth_path)
        cloth = cloth.resize((768,1024), Image.BICUBIC).convert('RGB')
        cloth.save(os.path.join('ACGPN/Data_preprocessing/test_color', cloth_name))
        u2net_run.infer(u2net, 'ACGPN/Data_preprocessing/test_color', 'ACGPN/Data_preprocessing/test_edge')

        os.listdir('ACGPN/inputs/img')
        start_time = time.time()
        img = Image.open(profile_path)
        img = img.resize((768,1024), Image.BICUBIC)
        img.save(os.path.join('ACGPN/Data_preprocessing/test_img', profile_name))

        resize_time = time.time()
        print('Resized image in {}s'.format(resize_time - start_time))

        os.system("python3 ACGPN/Self-Correction-Human-Parsing-for-ACGPN/simple_extractor.py --dataset 'lip' --model-restore 'ACGPN/lip_final.pth' --input-dir 'ACGPN/Data_preprocessing/test_img' --output-dir 'ACGPN/Data_preprocessing/test_label'")
        parse_time = time.time()
        print('Parsing generated in {}s'.format(parse_time - resize_time))

        pose_path = os.path.join('ACGPN/Data_preprocessing/test_pose', profile_name.replace('.png', '_keypoints.json'))
        generate_pose_keypoints(profile_path, pose_path)
        pose_time = time.time()
        print('Pose map generated in {}s'.format(pose_time - parse_time))

        os.system('rm -rf ACGPN/Data_preprocessing/test_pairs.txt')

        with open('ACGPN/Data_preprocessing/test_pairs.txt','w') as f:
            f.write(profile_name + " " + cloth_name)

        os.system('python ACGPN/test.py --how_many 1 --gpu_ids -1')

        path_dir = 'ACGPN/results/test/try-on/'
        result = path_dir + os.listdir(path_dir)[0]

        return result 

def delete_fitting(request):
    os.system('rm -rf ACGPN/Data_preprocessing/test_color/*')
    os.system('rm -rf ACGPN/Data_preprocessing/test_edge/')
    os.system('rm -rf ACGPN/Data_preprocessing/test_img/*')
    os.system('rm -rf ACGPN/Data_preprocessing/test_label/*')
    os.system('rm -rf ACGPN/Data_preprocessing/test_mask/*')
    os.system('rm -rf ACGPN/Data_preprocessing/test_pose/*')
    os.system('rm -rf ACGPN/inputs/cloth/*')
    os.system('rm -rf ACGPN/inputs/img/*')