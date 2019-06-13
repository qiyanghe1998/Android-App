### Lecture2

#### Yi Zhao 11612917

#### Lecturer: Tan, Shin Hwei



**Software configuration management (SCM)**

Four aspects 

- Change control 
- Version control 
- Building 
- Releasing 



A **Version Control System** is a software system that keeps track of the changes made to a set of files so that you can recall a specific version. 



**Commands for usual VCS:**



***Creating local copy:***

svn checkout <address-remote> <local-dir>

git checkout <branch-name>



***Commit Local Changes:***

svn commit –m “msg” 

git commit –m “msg” 



***Update Local Copy:***

svn up

git pull upstream master



***telling svn/git about a new file to track***

svn add <filename>

git add <filename>



- svn st: shows the status of files in the current svn directory
- svn rm: removes a file from the set of tracked files (will be removed on the remote server as well) 
- svn mv: moves a file from one directory to another (or renames if in same directory) 
- svn diff: diff between two revisions, or diff a file to see uncommitted local changes.
- svn propset svn:ignore –R *.class:  ignore .class files in commit



In Subversion each modification to the central repo **incremented the version # of the overall repo**.

Instead,  Git **generates a unique SHA-1 hash – 40 character string of hex digits**, **for every commit**.  Refer to commits by this ID rather than a version number. 

![1559818785600](C:\Users\Joy\AppData\Roaming\Typora\typora-user-images\1559818785600.png)



![1559819343838](C:\Users\Joy\AppData\Roaming\Typora\typora-user-images\1559819343838.png)



**What happens if two people are changing same software at same time?** 

- One solution (locking): Impossible!  First person must lock software before making changes.  Second person must wait until lock is released. 
- Another solution (merging): The second person to check in the software must merge the changes. Automatic merging works most of the time (but can silently introduce bugs).



**Check in quickly!** 

- The longer you have code checked out 
- The more you interfere with others (locking) 
- The harder it is to merge (merging) 



**Need a tool** 

- make - original Unix tool
- ant - Java based, uses XML 
- mvn – used by Jenkins 
- Gradle – Groovy and Kotlin based



**Product =** 

- set of components/documents 
- Code 
- Test suites 
- Operation manuals (admins, end-users) 
- Requirements 
- Specifications 
- Design documentation 
- Plans/schedules



**Branches**

- Avoid (long-lived) branches if possible 
- Good reasons to branch
  - Fixing bugs in customer version 
  - Experimental version 
  - Political fights



**It is bad to make branches to support different hardware platform or different customer**



**SCM Has many aspects** 

- Identification (Versions, baseline, release)
- Controls (Baseline is a software configuration item that has been reviewed and agreed upon, and that can be changed only through formal change control procedures, release should be baseline)
- status accounting (Reporting the status of components and change requests )
- Audit and review ()



**Change control** 

- Change control authority - decides which changes should be carried out 



**Various tests**

- Smoke test : Ensure that the system still runs after you make a change

- Unit test : Ensure that a module is not broken after you make a change 
- Regression test : Ensure that existing code doesn’t get worse as you make other improvements

