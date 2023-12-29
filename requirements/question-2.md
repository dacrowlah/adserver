Ryan Crowley <dacrowlah@gmail.com>

May 23, 2023 to Interviewing Team Members

Ok, I don't think I have any more questions regarding implementation of the outlined features, and it is as done as it will be without being turned into a fully baked adserver.

https://github.com/dacrowlah/adserver

I had a friend of mine follow the instructions for building/running/testing that are on the README, he did have issues with getting the JDK 17 available, but it was a simple fix, and he had it going in less than 5 minutes.  I am assuming of course that you have a mac, as I don't have a windows machine available for testing.

I didn't check test coverage, but it's fairly comprehensive, and all of the curl tests in the doc work in that order.

I look forward to hearing your feedback!

-Ryan


> On Tue, May 23, 2023 Lead Interviewing Engineer wrote:
>
> Hi Ryan,
>
> Good question!
> 
> For the take-home, I think the `impression-url` is valid if-and-only-if the url was previously returned from ads-server.
>
> Thanks,
>
> Lead Interviewing Engineer
>
> >  On Tue, May 23, 2023 Ryan Crowley wrote:
> >
> > I have another (hopefully final) question - the document says:
> >
> > ```
> > If the impression-url is valid, return status code 200 and empty response body. 
> > If the impression-url is invalid, return status code 400 and empty response body.
> > ```
> >
> > 
> > What would be considered the definition of valid? Currently how I have it implemented is purely based on whether or not it's a valid campaign id.
> >
> > It could also be based on the start/end times of the campaign, but the impression tracking systems I've worked with have typically been whether or not it's a valid campaign, and wanted to see if you guys had a different idea about this?
> >
> > -Ryan