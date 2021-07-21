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
    ]
]
media_files = [
    [
        '../media/images/childempreg.jpg',
        '../media/images/promotion.png',
        '../media/videos/childe.mp4',
        '../media/videos/chiIde.mp4',
        '../media/videos/persomna.mp4'
    ],
    [
        '../media/images/johnlee.png'
    ],
    [
        '../media/images/bornthisway.png'
    ],
    [
        '../media/images/tarompreg.png'
    ]
]


@bot.event
async def on_ready():
    print(f"Logged in as {bot.user}")


@bot.event
async def on_message(message: discord.Message):
    if message.author == bot.user:
        return

    processed_message = "".join(message.content.split()).lower()

    for i in range(4):
        if any(word in processed_message for word in trigger_words[i]):
            file = discord.File(determine_file_to_send(media_files[i], i))
            if file is not None:
                await message.channel.send(file=file)
            break


def determine_file_to_send(files: list, index: int) -> Optional[str]:
    if index == 0:
        if random.randint(0, 9) % 10 == 0:
            return random.choice(files[2:])

        now = datetime.now()
        return files[now.day == birthday.day and now.month == birthday.month]

    if index == 1 or index == 2:
        return files[0]

    if index == 3 and random.randint(0, 2) % 3 == 0:
        return files[0]

    return None


def main():
    load_dotenv()
    bot.run(os.environ['TOKEN'])


if __name__ == '__main__':
    main()
