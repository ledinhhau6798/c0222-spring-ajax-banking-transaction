class App{
    static DOMAIN_SERVER = window.origin;
    static API_SERVER = this.DOMAIN_SERVER + '/api';
  
    static API_CUSTOMER = this.API_SERVER + '/customers';
    static API_DEPOSIT = this.API_SERVER + '/deposits';
    static API_WITHDRAW = this.API_SERVER + '/withdraws';
    static API_TRANSFER = this.API_SERVER + '/transfers';

    static showSuccessAlert(t) {
      Swal.fire({
        icon: 'success',
        title: t,
        position: 'top-end',
        showConfirmButton: false,
        timer: 1500,
      });
    }
}

class Customer {
    constructor(id, fullName, email, phone, balance, address) {
      this.id = id;
      this.fullName = fullName;
      this.email = email;
      this.phone = phone;
      this.balance = balance;
      this.address = address;
    }
  }
  
 class Deposit {
    constructor(id, customerId, transactionAmount) {
      this.id = id;
      this.customerId = customerId;
      this.transactionAmount = transactionAmount;
    }
  }
class Withdraw{
    constructor(id, customerId, transactionAmount) {
        this.id = id;
        this.customerId = customerId;
        this.transactionAmount = transactionAmount;
      }
}
class Transfer {
  constructor (id, fees, feesAmount, transactionAmount, transferAmount, senderId, recipientId) {
      this.id = id;
      this.fees = fees;
      this.feesAmount = feesAmount;
      this.transactionAmount = transactionAmount;
      this.transferAmount = transferAmount;
      this.senderId = senderId;
      this. recipientId = recipientId;
      
  }
}
