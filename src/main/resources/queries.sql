Select  ip , count(1) as val  from AccessLog where accessDate between '2017-01-01.13:00:00' and '2017-01-01.14:00:00'
group by ip  having count(1)  > 100
