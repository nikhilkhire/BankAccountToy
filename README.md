# BankAccountToy
Added following functionality
1.Authentication 
2.Deposit money 
3.Transfer some money 
4.Show balance 
5.Show transactions 

I have used H2 datanase for now but we can easily configure any relational database instead.

As an initial commit there are no test cases added.

Below is sample request for transfer:
{
 "userName":"nikhilk",
 "password":"YBmsyk1muXIbu3bPucQ6JT1Kx9DgGgrLTwKk+bN2vLg=",
 "ibanFrom":"112233665544",
  "ibanTo":"987456654789",
 "amount":"10"
}

**Data Set up
****Users**
INSERT INTO PROFILE VALUES (1,'Nikhil','Khire','1a2b3c','YBmsyk1muXIbu3bPucQ6JT1Kx9DgGgrLTwKk+bN2vLg=',12345,'nikhilk','abcd');
INSERT INTO PROFILE VALUES (2,'Nikhil2','Khire2','1a2b3c2','YBmsyk1muXIbu3bPucQ6JT1Kx9DgGgrLTwKk+bN2vLg=',123452,'nikhilk2','abcd2');

**Accounts**
insert into account values('987456654789',32015.25,'€',1,1 );
insert into account values('774411225588',32015.25,'€',2,1 );
insert into account values('112233665544',32015.25,'€',1,2 )

**Users_Accounts**
insert into PROFILE_ACCOUNT_LIST values(1,'987456654789');
insert into PROFILE_ACCOUNT_LIST values(1,'774411225588');
insert into PROFILE_ACCOUNT_LIST values(2,'112233665544');
