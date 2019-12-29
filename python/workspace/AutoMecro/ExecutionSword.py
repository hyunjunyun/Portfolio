import pyautogui as pag
import mss, cv2
import numpy as np

left_icon_pos = {'left': 140, 'top': 710, 'width': 105, 'height': 105}
right_icon_pos = {'left': 340, 'top': 705, 'width': 105, 'height': 105}
left_button = [70, 850]
right_button = [480, 850]


def compute_icon_type(img):
    mean = np.mean(img, axis=(0, 1))

    if mean[0] > 50 and mean[0] < 55 and mean[1] > 50 and mean[1] < 55 and mean[2] > 50 and mean[2] < 55:
        result = 'BOMB'
    elif mean[0] > 250 and mean[1] > 85 and mean[1] < 110 and mean[2] > 250:
        result = 'SWORD'
    elif mean[0] > 100 and mean[0] < 130 and mean[1] > 150 and mean[1] < 200 and mean[2] > 90 and mean[2] < 110:
        result = 'POISON'
    elif mean[0] > 210 and mean[0] < 230 and mean[1] > 200 and mean[1] < 225 and mean[2] > 120 and mean[2] < 135:
        result = 'JEWEL'

    return result


while True:
    x, y = pag.position()
    position_str = 'X: ' + str(x) + ' Y: ' + str(y)
    # print(position_str)

with mss.mss() as sct:
    left_img = np.array(sct.grab(left_icon_pos))[:, :, :3]
    right_img = np.array(sct.grab(right_icon_pos))[:, :, :3]

    cv2.imshow('left_img', left_img)
    cv2.imshow('right_img', right_img)
    cv2.waitKey(0)

    left_icon = compute_icon_type(left_img)
    right_icon = compute_icon_type(right_img)

    if left_icon == 'SWORD' and (right_icon == 'BOMB' or right_icon == 'POISON'):
        print('TAP LEFT!')
    elif right_icon == 'SWORD' and (left_icon == 'BOMB' or left_icon == 'POISON'):
        print('TAP RIGHT!')
    elif left_icon == 'JEWEL' and right_icon == 'JEWEL':
        print('FEVER!')
    else:
        print('FAIL!')



