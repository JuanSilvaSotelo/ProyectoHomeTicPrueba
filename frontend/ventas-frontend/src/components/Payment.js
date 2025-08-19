import React from 'react';
import StripeCheckout from 'react-stripe-checkout';
import axios from 'axios';

const Payment = ({ amount }) => {
  const publishableKey = 'pk_test_YOUR_STRIPE_PUBLISHABLE_KEY';

  const onToken = (token) => {
    axios
      .post(
        'http://localhost:8080/api/payment/charge',
        {
          // Changed to POST
          token: token.id,
          amount: amount,
        },
        {
          headers: {
            token: token.id, // Pass token in headers
            amount: amount, // Pass amount in headers
          },
        }
      )
      .then((response) => {
        alert('Payment Success');
        console.log(response.data);
      })
      .catch((error) => {
        alert('Payment Failed');
        console.error(error);
      });
  };

  return (
    <StripeCheckout
      token={onToken}
      stripeKey={publishableKey}
      amount={amount * 100} // Amount in cents
      currency="USD"
      name="Ventas App"
      description="Payment for your order"
    />
  );
};

export default Payment;
