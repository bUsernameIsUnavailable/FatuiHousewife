import os
from dotenv import load_dotenv
from typing import Optional

import random
from datetime import datetime

import discord

bot = discord.Client()
birthday = datetime.strptime('20/07/2021', '%d/%m/%Y')
trigger_words = [
    [
        "baby",
        "fatui",
        "housewife",
        "mpreg",
        "child",
        "tartaglia",
        "ajax"
    ],
    [
        "zhongli",
        "johnlee"
    ],
    [
        "birth",
        "born"
    ],
    [
        "yansim",
        "yanderesimulator",
        "taro",
        "yamada",
        "senpai"
    ],
    [
        "ayaka",
        "kamisato"
    ],
    [
        "diluc"
    ],
    [
        "kinky",
        "horny",
        "bonk",
        "booba"
    ]
]
media_files = [
    [
        '../media/images/childempreg.jpg',
        '../media/images/promotion.png',
        '../media/images/drawnpreg.png',
        '../media/images/expecting.png',
        '../media/videos/childe.mp4',
        '../media/videos/chiIde.mp4',
        '../media/videos/persomna.mp4',
        '../media/images/shapeofyou.gif',
        '../media/images/fat_ui.gif'
    ],
    [
        '../media/images/johnlee.png',
        '../media/images/daddy_is_back.png'
    ],
    [
        '../media/images/bornthisway.png'
    ],
    [
        '../media/images/tarompreg.png'
    ],
    [
        '../media/videos/dabadayaka.mp4'
    ],
    [
        '../media/images/strawluc.gif'
    ],
    [
        '../media/images/bonk_meteor.gif',
        '../media/images/end_user_bonk.gif'
    ]
]
nsfw_files = [
    [
        '../media/images/80084.png'
    ],
    [
        '../media/images/80084.png'
    ],
    [],
    [],
    [],
    [],
    []
]


@bot.event
async def on_ready():
    print(f"Logged in as {bot.user}")


@bot.event
async def on_message(message: discord.Message):
    if message.author == bot.user:
        return

    processed_message = "".join(message.content.split()).lower()

    for i in range(7):
        if any(word in processed_message for word in trigger_words[i]):
            file = determine_file_to_send(i, message.channel.nsfw)
            if file is not None:
                try:
                    await message.channel.send(file=discord.File(file))
                except Exception as e:
                    print(f"{message.guild.name}: {e}")
            break


def determine_file_to_send(index: int, is_nsfw: bool) -> Optional[str]:
    if index == 0:
        if random.randint(0, 3) % 4 == 0:
            return random.choice(media_files[index][2:] + is_nsfw * nsfw_files[index])

        now = datetime.now()
        return media_files[index][now.day == birthday.day and now.month == birthday.month]

    if index == 1:
        return random.choice(media_files[index] + is_nsfw * nsfw_files[index])

    if index in [2, 4, 5]:
        return media_files[index][0]

    if index == 3 and random.randint(0, 9) % 10 == 0:
        return media_files[index][0]

    if index == 6:
        return random.choice(media_files[index])

    return None


def main():
    load_dotenv()
    bot.run(os.environ['TOKEN'])


if __name__ == '__main__':
    main()
