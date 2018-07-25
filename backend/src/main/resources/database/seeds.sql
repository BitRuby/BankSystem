INSERT INTO bank.bank_account (id, account_no, is_active, first_name, last_name, money_amount, money_blocked) VALUES
  (1, '29116022020000000311935598', true, 'Norbert', 'Wychowski', 10000.00, 0.00),
  (2, '74105014161000009203793907', true, 'Tomasz', 'Woźniak', 10000.00, 0.00);

INSERT INTO bank.transfer (id, account_no, booking_date, create_time, status, title, transfer_type, value, currency, account_id) VALUES
  (1, '29116022020000000311935598', null, '2018-07-16 09:42:12', 'PENDING', 'przelew do Norberta 1', 'OUTGOING', 100.00, 'PLN', 2),
  (2, '74105014161000009203793907', null, '2018-07-16 09:42:10', 'PENDING', 'przelew do Tomka 1', 'OUTGOING', 100.00, 'PLN', 1),
  (3, '29116022020000000311935598', null, '2018-07-16 09:42:12', 'PENDING', 'przelew do Norberta 2', 'OUTGOING', 100.00, 'PLN', 2),
  (4, '74105014161000009203793907', null, '2018-07-19 09:42:10', 'PENDING', 'przelew do Tomka 2', 'OUTGOING', 100.00, 'PLN', 1),
  (5, '29116022020000000311935598', null, '2018-07-19 09:42:12', 'PENDING', 'przelew do Norberta 3', 'OUTGOING', 100.00, 'PLN', 2),
  (6, '74105014161000009203793907', null, '2018-07-19 09:42:10', 'PENDING', 'przelew do Tomka 3', 'OUTGOING', 100.00, 'PLN', 1),
  (7, '29116022020000000311935598', null, '2018-07-19 09:42:12', 'PENDING', 'przelew do Norberta 4', 'OUTGOING', 100.00, 'PLN', 2),
  (8, '74105014161000009203793907', null, '2018-07-21 09:42:10', 'PENDING', 'przelew do Tomka 4', 'OUTGOING', 100.00, 'PLN', 1),
  (9, '29116022020000000311935598', null, '2018-07-21 09:42:12', 'PENDING', 'przelew do Norberta 5', 'OUTGOING', 100.00, 'PLN', 2),
  (10, '74105014161000009203793907', null, '2018-07-22 09:42:10', 'PENDING', 'przelew do Tomka 5', 'OUTGOING', 100.00, 'PLN', 1),
  (11, '29116022020000000311935598', null, '2018-07-22 09:42:12', 'PENDING', 'przelew do Norberta 6', 'OUTGOING', 100.00, 'PLN', 2),
  (12, '74105014161000009203793907', null, '2018-07-22 09:42:10', 'PENDING', 'przelew do Tomka 6', 'OUTGOING', 100.00, 'PLN', 1),
  (13, '29116022020000000311935598', null, '2018-07-22 09:42:12', 'PENDING', 'przelew do Norberta 7', 'OUTGOING', 100.00, 'PLN', 2),
  (14, '74105014161000009203793907', null, '2018-07-23 09:42:10', 'PENDING', 'przelew do Tomka 7', 'OUTGOING', 100.00, 'PLN', 1),
  (15, '29116022020000000311935598', null, '2018-07-23 09:42:12', 'PENDING', 'przelew do Norberta 8', 'OUTGOING', 100.00, 'PLN', 2),
  (16, '74105014161000009203793907', null, '2018-07-23 09:42:10', 'PENDING', 'przelew do Tomka 8', 'OUTGOING', 100.00, 'PLN', 1),
  (17, '29116022020000000311935598', null, '2018-07-23 09:42:12', 'PENDING', 'przelew do Norberta 9', 'OUTGOING', 100.00, 'PLN', 2),
  (18, '74105014161000009203793907', null, '2018-07-23 09:42:10', 'PENDING', 'przelew do Tomka 9', 'OUTGOING', 100.00, 'PLN', 1),
  (19, '29116022020000000311935598', null, '2018-07-24 09:42:12', 'PENDING', 'przelew do Norberta 10', 'OUTGOING', 100.00, 'PLN', 2),
  (20, '74105014161000009203793907', null, '2018-07-24 09:42:10', 'PENDING', 'przelew do Tomka 10', 'OUTGOING', 100.00, 'PLN', 1),
  (21, '29116022020000000311935598', null, '2018-07-24 09:42:12', 'PENDING', 'przelew do Norberta 11', 'OUTGOING', 100.00, 'PLN', 2),
  (22, '74105014161000009203793907', null, '2018-07-24 09:42:10', 'PENDING', 'przelew do Tomka 11', 'OUTGOING', 100.00, 'PLN', 1),
  (23, '29116022020000000311935598', null, '2018-07-25 09:42:12', 'PENDING', 'przelew do Norberta 12', 'OUTGOING', 100.00, 'PLN', 2),
  (24, '74105014161000009203793907', null, '2018-07-25 09:42:10', 'PENDING', 'przelew do Tomka 12', 'OUTGOING', 100.00, 'PLN', 1);