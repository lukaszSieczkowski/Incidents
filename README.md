Incidents Reporting Tool is a system for reporting Safety Observations in production companies.
Technologies used:
- Java 8
- JPA
- MySQL
- Spring
- JSP(JSTL)
- a little bit JavaScript(Google Charts and Date Picker)

Allow for reporting observations in few different locations, categories and with different categories of personel.

Application allow for work 2 types of user: User and Administrator.
- User have permissions to:
  * reports safety observations,
  * edits safety observations reported by himself as long they are OPEN
  * has access to all reported by himself safety incidents
  * has access to statistics describing reported safety observations
  * can change his own password
- Administrator have permissions to:
  * can see all reported observations,
  * has access to statistics for all reported observations,
  * can edit all reported incidents,
  * can approve or refuse reported incident. If incident is not approved, then is not visible 
    in statistics 
  * can add new user
  * can reset users password,
  * can lock or unlock any user
  * can change permissions for any user to Administrator
- List of reported safety observations may be sorted in few categories and filtered according conducted data
- Statistics are presented as 3 types of Charts (Google Charts) and my be filtered according conducted data.
- Actions like:
  * password change
  * safety observation report,
  * add new user
  * lock/unlock user
are reported by email message.
- First password for new user is generated from random letters and numbers.
- In database password is coded by MD5