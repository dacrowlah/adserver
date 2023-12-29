On Thu, May 18, 2023 Ryan Crowley wrote:

Well, it's not that much more work, really; I would just need to add 1 field to the campaign for capping strategy (campaign or user), and one to the request for a user identifier. My concern was more around what tooling you already have built to test this. I didn't want to be the guy coming in like "change all your stuff for me!"

I anticipated the campaign capping, and went ahead and did that.

BTW, this is fully built and working, I need to test more today to be sure. Next, there are parts I want to think about more and refine, and more test cases to write.


> On Thu, May 18, 2023 Lead Interviewing Engineer wrote:
> 
> Hi Ryan,
>
> Please use the max impressions value at the campaign level.
>
> Thank you for noticing it. It is intentional that we leave out "user" in the take-home. It was there in our first version. Later, the team found it was too much work for a take-home, so we took it out.
>
> spoiler alert, in the system design interview, we will talk a lot about users.
>
> Thank you.
>
> Lead Interviewing Engineer
> >
> > On Thu, May 18, 2023 Ryan Crowley wrote:
> >
> > Ok, I do have a question now:
> >
> > I am wondering about the line in the document that says "3. The user from the ad request has been impressed by the campaign less than the campaign's
"max_impression."
> >
> > Given that none of the example requests give any sort of data in the request body to indicate a particular user, I can either make something up and add a field to the request body to identify the user somehow.... or I can use that max impressions value as a campaign level impression cap.
> >
> > Let me know what you think, and I'll go with that.