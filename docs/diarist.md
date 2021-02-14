### Can you describe the app a litte bit more ?
Sure. I like to think it's split in 3 separate functions:
1. A notification or journal "prompt"
1. A journal manager, to CRUD all of your journal entries
1. A (not so) fancy viewer for all your journal entries

The "prompt" is a whatsapp message you get everyday.
It's purpose is both to remind you to write every day and also to inspire some responses (There's a difference between "Remember to write your diary" and "How were your alergies today?")

The manager consists of a REST API to write and read all your entries. Not much else to say

The viewer is meant to create a pleasant way to review your old entries. It currently doesn't do much but I have big plans for it in the future

### Why did you start this project ?

I've been thinking about diaries since I saw that [Loretta](https://youtu.be/6xSxXiHwMrg) superbowl commercial from Google (If you haven't seen it, go do that now, it's really good).
Of course, once the game was over, my first instinct was to `!youtube how to write a diary`<sup>[[1]](#1)</sup> which did lead to some [awesome examples](https://youtu.be/fPkPesz-t_Q).
However maintining something like that, specially if you try to make it as pretty as those youtube videos, is *really hard* so I just gave up.

When I first heard about the hackathon I started thinking of ideas for a project that used whatsapp and came up with a couple of them:
* Maybe some kind of image recognition app: You take a picture of an insect, whatsapp it to a number and it responds with the name of the animal! How cool would that be?
* Maybe a notification system for small businesses? I order my lunch from a small shop everyday and they always just text me when the delivery guy arrives
* Maybe you could use whatsapp to take notes !

Of course the third one sounded the easiest, but I'm sure all professors and co-workers around the world would hate me if that ever became popular, so I thought maybe something more personal would work best.

### Why is this so much better than an app or an old-fashioned notebook ?

Well, I asked myself the same thing before starting the project and came up with basically this two reasons:
* A common recommendation when starting a diary is to [write as if you were writing to a friend](https://www.skillsyouneed.com/ps/diary-journal.html), so what if the platform you used to do that was the very same one ?
* You don't have to install anything, it's just a number you text ("It's just a number" might become my tagline)

### What was your process for building this?

At first I wasn't sure of what I wanted, so I started doodling on this ipad app [Concepts](https://concepts.app/en/).
You can see the results in the [layout](layout.pdf) file in the repo docs if you are interested.

Once I had a more clear idea I started looking at Twilio's examples and I found this [Appointment reminders](https://www.twilio.com/docs/sms/tutorials/appointment-reminders-java-spark) java spark app which seemed like a good baseline to build the project on top off.

### What's next for the Diarist app ?
Good question! I do have some ideas but we'll see what the future brings (If you want to collaborate or bounce off more ideas I would appreciate the input)

* Add configurable journal prompts. I think figuring out what to write about it's really hard and asking the right questions always helps.
* Add more ways to send updates. Email is an obvious candidate for the next integration.
* Create an "On this day" feature like the one on Facebook. Journal entries are there to be remembered and although I'm not a Facebook user anymore I do miss that feature.
* Make the notification schedule configurable. Do you want a message every day at 8 PM ? You got it! Do you want a message only on Sundays ? It's up to you
* Create "themes" for the viewer. Mainly based around the stuff [Mellow Days](https://www.youtube.com/channel/UCU3ZpZbnPqdfUVU5zAeofcQ) from youtube creates since I'm interested on the whole "Looks hand-drawn but it's actually generated digitally" kind of stuff
* Normalize all of the stored data. I've seen some of the projects like [Notable](https://github.com/notable/notable) use markdown as a way to avoid vendor lock-in, which is a win for everyone IMO
* Add an easy way to edit the journal entries. I'm sure I'm not the only that has twice as many typos when writing on my phone.
* Add support for pictures (and other media). [That old adage](https://medium.com/@dt/the-myth-and-reality-of-a-picture-is-worth-a-thousand-words-a4ea985f8932) is true after all.


# References

<a id="1">[1]</a> !youtube is one of those Duck Duck Go [Bangs!](https://duckduckgo.com/bang), a shortcut for website searches that works across browsers and platforms
