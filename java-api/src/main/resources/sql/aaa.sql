
select * from book_check where item_id in (select nid from book where bid = 1807)