use library;
SET FOREIGN_KEY_CHECKS=0;
SET SQL_SAFE_UPDATES = 0;

use library;
SET FOREIGN_KEY_CHECKS=0;
SET SQL_SAFE_UPDATES = 0;


create table users(
	username char(6) NOT NULL,
    password char(8) NOT NULL,
    name varchar(45)  NOT NULL,
    position varchar (45) NOT NULL,
    phone char(12) NOT NULL,
    PRIMARY KEY(username)
);

create table books(
	idBook char(7) NOT NULL,
    nameBook varchar(50) NOT NULL,
    author varchar(45) NOT NULL,
    typeBook varchar (45) NOT NULL,
    shelf varchar(7) NOT NULL,
    quantity int NOT NULL,
    PRIMARY KEY(idBook)
);

create table borrow(
	idBorrow char(6) NOT NULL,
    username char(6) NOT NULL,
    idBook char(7) NOT NULL,
    dateBorrow Date NOT NULL,
    dateDue Date NOT NULL,
    dateReturn Date,
    trangthai varchar(45),
    PRIMARY KEY(idBorrow),
    FOREIGN KEY(username) REFERENCES users(username),
    FOREIGN KEY(idBook) REFERENCES books(idBook)
);



--  Lấy danh sách Sinh viên
DELIMITER $$
DROP PROCEDURE IF EXISTS getAllStudent $$
CREATE PROCEDURE getAllStudent()
BEGIN
	SELECT * FROM  users WHERE users.position like "Sinh viên";
END;
DELIMITER;

-- Lấy danh sách Thủ thư 
DELIMITER $$
DROP PROCEDURE IF EXISTS getAllLecturer $$
CREATE PROCEDURE getAllLecturer()
BEGIN
	SELECT * FROM  users WHERE users.position like "Thủ thư";
END;
COMMIT;
DELIMITER;


-- Thêm sách
DELIMITER $$
START TRANSACTION;
CREATE DEFINER=`root`@`localhost` PROCEDURE `insertBook`(in id_book char(7), name_book varchar(50), name_author varchar(45),
	type_book varchar(45), no_shelf varchar(7), quant int)
BEGIN
	INSERT INTO library.books values(id_book, name_book, name_author, type_book, no_shelf, quant);
END;
commit;
DELIMITER;

-- Xóa sách
DELIMITER $$
START TRANSACTION;
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteBook`(in id_book char(7))
BEGIN
	DELETE FROM library.books where books.idBook = id_book;
END;
COMMIT;
DELIMITER;

-- Sửa sách
DELIMITER $$
START TRANSACTION;
CREATE DEFINER=`root`@`localhost` PROCEDURE `updateBook`(in id_book char(7), name_book varchar(50), name_author varchar(45), type_book varchar(45), no_shelf varchar(7), quant int)
BEGIN
	UPDATE library.books set books.nameBook = name_book, books.author = name_author, books.typeBook = type_book,
    books.shelf = no_shelf, books.quantity = quant  WHERE books.idBook = id_book;
END;
COMMIT;
DELIMITER;


-- Thêm người dùng
delimiter $$
START TRANSACTION;
create definer =`root`@`localhost` procedure`insertUser`(in usrname char(6),pass char(8),na varchar(45),pos varchar(45),pho char(12))
begin
	Insert into library.users values(usrname, pass, na, pos, pho);
end;
COMMIT;
delimiter;

-- Xóa người dùng
delimiter $$
START TRANSACTION;
create definer =`root`@`localhost` procedure`deleteUser`(in usrname char(6))
begin
	delete from library.users where users.username = usrname;
end;
COMMIT;
delimiter;

-- Sửa người dùng
delimiter $$
START TRANSACTION;
create definer =`root`@`localhost` procedure`updateUser`(in usrname char(6),pass char(8),na varchar(45),pos varchar(45),pho char(12))
begin
	update library.users  set users.password=pass,users.name=na, users.position=pos,users.phone=pho where users.username=usrname;
end;
COMMIT;
delimiter;


-- Thêm mượn
delimiter $$
START TRANSACTION;
create definer =`root`@`localhost` procedure`insertBorrow`(in id char(6),na char(6), idb char(7), dateb date, dated date, dater date)
begin
	Insert into library.borrow values(id,na,idb,dateb,dated,dater);
end;
COMMIT;
delimiter;

-- Xóa mượn
delimiter $$
START TRANSACTION;
create definer =`root`@`localhost` procedure`deleteBorrow`(in id char(6))
begin
	delete from library.borrow where borrow.idBorrow = id;
end;
COMMIT;
delimiter;

-- Sửa mượn
delimiter $$
START TRANSACTION;
create definer =`root`@`localhost` procedure`updateBorrow`(in id char(6),na char(6), idb char(7), dateb date, dated date, dater date)
begin
	update library.borrow  set borrow.username=na,borrow.idBook=idb, borrow.dateBorrow=dateb,borrow.dateDue=dated ,borrow.dateReturn=dater where borrow.idBorrow=id;
end;
COMMIT;
delimiter;


-- Tổng số lượng sách của thư viện
delimiter $$
START TRANSACTION;
CREATE FUNCTION totalBooks()
	RETURNS int
    DETERMINISTIC
BEGIN
    RETURN (SELECT SUM(quantity) FROM books);
END;
COMMIT;
delimiter;

select totalBooks();
-- Tổng số lượng sách của một kệ 'location'
delimiter $$
CREATE FUNCTION totalBooksInShelf(location varchar(7))
	RETURNS int
    DETERMINISTIC
BEGIN
	DECLARE quant int;
	SELECT SUM(quantity) into quant FROM books
    WHERE shelf=location;
    RETURN quant;
END;
delimiter;
drop function searchUser;


-- Search người dùng
delimiter $$
CREATE DEFINER=`root`@`localhost` FUNCTION `searchUser`(uname char(6)) 
	RETURNS int
    DETERMINISTIC
begin
	if(uname in (select username from library.users))
		then return 1;
		else return 0;
	end if;
end;
delimiter;

-- Search book
delimiter $$
CREATE DEFINER=`root`@`localhost` FUNCTION `searchBook`(tensach varchar(45)) 
	RETURNS int
	DETERMINISTIC
begin
	if(tensach in (select tenSach from library.books))
then return 1;
else return 0;
end if;
end;
delimiter;

delimiter $$
create trigger insert_account
after insert
on users for each row
begin
	
delimiter ;

select sum(quantity) from books;
call insertUser('CB0001', '123456', 'Trương Chấn Huy', 'Thủ thư', '0903321933');
call insertUser('CB0002', '123456', 'Nguyễn Khắc Duy', 'Thủ thư', '0908152508');
call insertUser('CB0003', '1234567', 'Trần Công Nhật', 'Thủ thư', '0702965321');
call insertUser('CB0004', '1234568', 'Liên Thị Cẩm Ni', 'Thủ thư', '0903321933');
call insertUser('CB0005', '1234589', 'Tạ Trần Ngọc Xuyến', 'Thủ thư', '0908155503');
call insertUser('SV0001', '0120123', 'Nguyễn Hoàng Gia Bảo', 'Sinh viên', '0903321620');
call insertUser('SV0002', '123123', 'Trương Hoàng Huy', 'Sinh viên', '0903312082');
call insertUser('SV0003', '1234123', 'Lý Chấn Huy', 'Sinh viên', '0903962017');
call insertUser('SV0004', '321321', 'Nguyễn Thị Bảo Ngọc', 'Sinh viên', '0903410077');
call insertUser('SV0005', '6543210', 'Trương Thị Trúc Ly', 'Sinh viên', '0703992118');
call insertUser('SV0006', '890890', 'Trần Thị Kim Anh', 'Sinh viên', '0702312001');
call insertUser('SV0007', '1234500', 'Nguyễn Hoàng Nhã Trân', 'Sinh viên', '0708932000');
call insertUser('SV0008', '0123456', 'Ngô Hoàng Uyên', 'Sinh viên', '0702345620');
call insertUser('SV0009', '012012', 'Lương Chí Cao', 'Sinh viên', '0903322932');
call insertUser('SV0010', '123123', 'Trương Vạn Phát', 'Sinh viên', '0763895423');
call insertUser('SV0011', '1234123', 'Lý Văn Toàn', 'Sinh viên', '0703112003');
call insertUser('SV0012', '321321', 'Thiềm Ngọc Uyên Nhi', 'Sinh viên', '0903511067');
call insertUser('SV0013', 'trucnhi', 'Thạch Thị Trúc Nhi', 'Sinh viên', '0703192169');
call insertUser('SV0014', 'nhumai2', 'Lữ Như Mai', 'Sinh viên', '0702312000');
call insertUser('SV0015', '1234550', 'Đỗ Lý Anh Thư', 'Sinh viên', '0708533000');
call insertUser('SV0016', '0123456', 'Đỗ Quốc Cơ', 'Sinh viên', '0394398001');
call insertUser('SV0017', '654321', 'Huỳnh Tuấn Dũng', 'Sinh viên', '0703192169');
call insertUser('SV0018', '890891', 'Trần Trung', 'Sinh viên', '0702312000');
call insertUser('SV0019', '1234501', 'Đỗ Phúc Sang', 'Sinh viên', '0708533000');
call insertUser('SV0020', '0123456', 'Trần Hoàng Anh', 'Sinh viên', '0394398001');
call insertUser('SV0021', '1231231', 'Phương Trịnh Anh Thư', 'Sinh viên', '0394397003');
call insertUser('SV0022', '0123457', 'Diệp Quốc Tín', 'Sinh viên', '0394118345');
call insertUser('SV0023', '6543112', 'Huỳnh Anh Kiệt', 'Sinh viên', '0703112179');
call insertUser('SV0024', '890123', 'Cao Tiến Dũng', 'Sinh viên', '0702212123');
call insertUser('SV0025', '1234501', 'Cao Thị Mỹ Tú', 'Sinh viên', '0708563010');
call insertUser('SV0026', '1231231', 'Trịnh Hoài Nam', 'Sinh viên', '0394317009');
call insertUser('SV0027', '0123457', 'Trần Văn Nam', 'Sinh viên', '0394110344');
call insertUser('SV0028', 'ctuctu', 'Nguyễn Trọng Nhân', 'Sinh viên', '0703182279');
call insertUser('SV0029', 'cantho', 'Nguyễn Mạnh Quỳnh', 'Sinh viên', '0702922128');
call insertUser('SV0030', 'dh1234', 'Nguyễn Thị Thủy Tiên', 'Sinh viên', '0708573012');
call insertUser('SV0031', 'zxczxc', 'Nguyễn Thị Hoài Thương', 'Sinh viên', '0394210334');
call insertUser('SV0032', 'daihocc', 'Nguyễn Trọng Quý', 'Sinh viên', '0703132676');
call insertUser('SV0033', 'abcdef', 'Bùi Thanh Tùng', 'Sinh viên', '0702992198');
call insertUser('SV0034', 'trthanh', 'Phạm Thị Thanh Trúc', 'Sinh viên', '0708553992');
call insertUser('SV0035', 'tinhngo', 'Ngô Thanh Tịnh', 'Sinh viên', '0394290333');
call insertUser('SV0036', 'daihocc', 'Bùi Thanh Xuân', 'Sinh viên', '0703134676');
call insertUser('SV0037', 'Abcdef', 'Võ Thị Ngọc Nhi', 'Sinh viên', '0394810340');
call insertUser('SV0038', 'khoi123', 'Võ Trần Khôi Nguyên', 'Sinh viên', '0708513192');
call insertUser('SV0039', 'tuongne', 'Nguyễn Bá Tường', 'Sinh viên', '0703334671');
call insertUser('SV0040', 'thepham', 'Phạm Ngọc Thể', 'Sinh viên', '0394818330');
call insertUser('SV0041', '789123', 'Châu Tuyết Nhi', 'Sinh viên', '0708503199');
call insertUser('SV0042', 'phucCT', 'Nguyễn Vạn Phúc', 'Sinh viên', '0703434476');
call insertUser('SV0043', '101010', 'Mai Công Thoại', 'Sinh viên', '0394410344');
call insertUser('SV0044', 'khanh92', 'Trần Quốc Khánh', 'Sinh viên', '0708515152');
call insertUser('SV0045', 'nhulieu', 'Nguyễn Như Liễu', 'Sinh viên', '0703964671');
call insertUser('SV0046', '1230123', 'Trương Xuân Anh', 'Sinh viên', '0394888333');
call insertUser('SV0047', '789123', 'Thái Văn Nam', 'Sinh viên', '0708563119');
call insertUser('SV0048', 'thinh10', 'Nguyễn Gia Thịnh', 'Sinh viên', '0703513152');
call insertUser('SV0049', 'vanninh', 'Tạ Văn Ninh', 'Sinh viên', '0703954672');
call insertUser('SV0050', '567567', 'Vũ Trọng Tính', 'Sinh viên', '0394898323');
call insertUser('SV0051', 'kimtho', 'Lữ Kim Thơ', 'Sinh viên', '0708585318'); -- 50 users


call insertBook('CTU0001', 'Lối sống tối giản của người Nhật', 'Sasaki Fumio', 'Lối sống', 'A1', 3);
call insertBook('CTU0002', 'Nhà giả kim', 'Paulo Coelho', 'Kỹ năng sống', 'A1', 5);
call insertBook('CTU0003', 'Trí tuệ do thái', 'Eran Katz', 'Kỹ năng sống', 'A1', 2);
call insertBook('CTU0004', 'Tâm lý học về tiền', 'Morgan Housel', 'Kỹ năng sống', 'A1', 1);
call insertBook('CTU0005', 'Còn chút gì để nhớ', 'Nguyễn Nhật Ánh', 'Văn học', 'A1', 7);
call insertBook('CTU0006', 'Mắt biếc', 'Nguyễn Nhật Ánh', 'Văn học', 'A1', 9);
call insertBook('CTU0007', 'Kiêu hãnh và định kiến', 'Jane Austen', 'Văn học', 'A1', 9);
call insertBook('CTU0008', 'Chí Phèo', 'Nam Cao', 'Văn học', 'A1', 4);
call insertBook('CTU0009', 'Tư duy như Churchill', 'Daniel Smith', 'Kỹ năng sống', 'A1', 1);
call insertBook('CTU0010', 'Ổn định hay tự do', 'Trương Học Vĩ', 'Kỹ năng sống', 'A1', 2);
call insertBook('CTU0011', 'Hoa hồng xứ khác', 'Nguyễn Nhật Ánh', 'Văn học', 'A1', 3);
call insertBook('CTU0012', 'Einstein cuộc đời và vũ trụ', 'Walter Isaacson', 'Tiểu sử', 'A1', 3);
call insertBook('CTU0013', 'Quân vương – thuật cai trị', 'Niccolò Machiavelli', 'Chính trị', 'A1', 2);
call insertBook('CTU0014', 'Giá trị của sự tử tế', 'Piero Ferrucci', 'Kỹ năng sống', 'A1', 8);
call insertBook('CTU0015', 'Những người hàng xóm', 'Nguyễn Nhật Ánh', 'Văn học', 'A1', 8);
call insertBook('CTU0016', 'Tiểu sử Steve Jobs', 'Walter Isaacson', 'Tiểu sử', 'A1', 3); 
-- A1 70 books

call insertBook('CTU0017', 'Người giàu nhất thành Babylon', 'George S. Clason', 'Kỹ năng sống', 'A2', 5);
call insertBook('CTU0018', 'Người bán hàng vĩ đại nhất thế giới', 'Og Mandino', 'Kỹ năng sống', 'A2', 3);
call insertBook('CTU0019', 'Giới tinh hoa quyền lực', 'Charles Wright Mills', 'Chính trị', 'A2', 2);
call insertBook('CTU0020', 'Bàn về khế ước xã hội', 'Jean - Jacques Rousseau', 'Chính trị', 'A2', 2);
call insertBook('CTU0021', 'Tương lai của quyền lực', 'Joseph S. Nye Jr', 'Chính trị', 'A2', 4);
call insertBook('CTU0022', 'Kiểm soát quyền lực nhà nước', 'GS. TS. Nguyễn Đăng Dung', 'Chính trị', 'A2', 4);
call insertBook('CTU0023', 'Tự chỉ trích', 'Nguyễn Văn Cừ', 'Chính trị', 'A2', 3);
call insertBook('CTU0024', 'Đường Cách Mệnh', 'Nguyễn Ái Quốc', 'Chính trị', 'A2', 2);
call insertBook('CTU0025', 'Sapiens lược sử loài người', 'Yuval Noah Harari', 'Lịch sử', 'A2', 9);
call insertBook('CTU0026', 'Homo Deus: lược sử tương lai', 'Yuval Noah Harari', 'Lịch sử', 'A2', 9);
call insertBook('CTU0027', 'Kochland - Đế chế Koch', 'Christopher Leonard', 'Lịch sử', 'A2', 2);
call insertBook('CTU0028', 'Sự giàu và nghèo của các dân tộc', 'David S.Landes', 'Lịch sử', 'A2', 7);
call insertBook('CTU0029', 'Lịch sử tiền tệ', 'Jack Weatherford', 'Lịch sử', 'A2', 4);
call insertBook('CTU0030', 'Những con đường tơ lụa', 'Peter Frankopan', 'Lịch sử', 'A2', 3);
call insertBook('CTU0031', 'Đệ nhất thế chiến', 'Robert Leckie', 'Lịch sử', 'A2', 2);
call insertBook('CTU0032', 'Lịch sử văn minh Trung Hoa', 'Will Durant', 'Lịch sử', 'A2', 5);
call insertBook('CTU0033', 'Lịch sử văn minh Ả Rập', 'Will Durant', 'Lịch sử', 'A2', 5);
call insertBook('CTU0034', 'Lịch sử văn minh Ấn Độ', 'Will Durant', 'Lịch sử', 'A2', 5);
call insertBook('CTU0035', 'Nguồn gốc văn minh', 'Will Durant', 'Lịch sử', 'A2', 5); 

call insertBook('CTU0036', 'Hai số phận', 'Jeffrey Archer', 'Văn học', 'A3', 5);
call insertBook('CTU0037', 'Một chỗ trong đời', 'Annie Ernaux', 'Văn học', 'A3', 5);
call insertBook('CTU0038', 'Tiếng núi', 'Kawabata Yasunari', 'Văn học', 'A3', 5);
call insertBook('CTU0039', 'Đồi gió hú', 'Emily Bronte', 'Văn học', 'A3', 5);
call insertBook('CTU0040', 'Túp lều Bác Tom', 'Harriet Beecher Stowe', 'Văn học', 'A3', 5);
call insertBook('CTU0041', 'Những ngôi sao Eger', 'Gárdonyi Géza', 'Văn học', 'A3', 5);
call insertBook('CTU0042', 'Cuốn theo chiều gió', 'Margaret Mitchell', 'Văn học', 'A3', 5);
call insertBook('CTU0043', 'Trà hoa nữ', 'Alexandre Dumas', 'Văn học', 'A3', 5);
call insertBook('CTU0044', 'Những người phụ nữ bé nhỏ', 'Louisa May Alcott', 'Văn học', 'A3', 5);
call insertBook('CTU0045', 'Chuông nguyện hồn ai', 'Ernest Miller Hemingway', 'Văn học', 'A3', 5);
call insertBook('CTU0046', 'Ba người lính Ngự Lâm', 'Alexandre Dumas', 'Văn học', 'A3', 10);
call insertBook('CTU0047', 'Bá tước Monte Cristo', 'Alexandre Dumas', 'Văn học', 'A3', 10); -- A3 70


call insertBorrow('000001', 'SV0001', 'CTU0001', '2023-04-01', '2023-04-11', '2023-04-10','Đã trả');
call insertBorrow('000002', 'SV0002', 'CTU0001', '2023-04-01', '2023-04-11', '2023-04-11','Đã trả');
call insertBorrow('000003', 'SV0003', 'CTU0005', '2023-04-02', '2023-04-12', '2023-04-11','Đã trả');
call insertBorrow('000004', 'SV0004', 'CTU0008', '2023-04-02', '2023-04-12', '2023-04-09','Đã trả');
call insertBorrow('000005', 'SV0005', 'CTU0003', '2023-04-04', '2023-04-14', '2023-04-14','Đã trả');
call insertBorrow('000006', 'SV0006', 'CTU0006', '2023-04-05', '2023-04-15', '2023-04-14','Đã trả');
call insertBorrow('000007', 'SV0007', 'CTU0004', '2023-04-06', '2023-04-16', '2023-04-15','Đã trả');
call insertBorrow('000008', 'SV0008', 'CTU0009', '2023-04-10', '2023-04-20', '2023-04-19','Đã trả');
call insertBorrow('000009', 'SV0002', 'CTU0010', '2023-04-13', '2023-04-23', '2023-04-21','Đã trả');
call insertBorrow('000010', 'SV0001', 'CTU0006', '2023-04-13', '2023-04-23', '2023-04-22','Đã trả');
call insertBorrow('000011', 'SV0027', 'CTU0047', '2023-04-13', '2023-04-23', '2023-04-15','Đã trả');
call insertBorrow('000012', 'SV0020', 'CTU0011', '2023-04-13', '2023-04-23', '2023-04-18','Đã trả');
call insertBorrow('000013', 'SV0013', 'CTU0030', '2023-04-13', '2023-04-23', '2023-04-18','Đã trả');
call insertBorrow('000014', 'SV0014', 'CTU0030', '2023-04-13', '2023-04-23', '2023-04-18','Đã trả');
call insertBorrow('000015', 'SV0035', 'CTU0013', '2023-04-13', '2023-04-23', '2023-04-23','Đã trả');
call insertBorrow('000016', 'SV0026', 'CTU0022', '2023-04-13', '2023-04-23', '2023-04-22','Đã trả');
call insertBorrow('000017', 'SV0017', 'CTU0044', '2023-04-13', '2023-04-23', '2023-04-20','Đã trả');
call insertBorrow('000018', 'SV0038', 'CTU0039', '2023-04-13', '2023-04-23', '2023-04-19','Đã trả');
call insertBorrow('000019', 'SV0019', 'CTU0019', '2023-04-13', '2023-04-23', '2023-04-21','Đã trả');
call insertBorrow('000020', 'SV0015', 'CTU0036', '2023-04-14', '2023-04-24', '2023-04-22','Đã trả');
call insertBorrow('000021', 'SV0021', 'CTU0031', '2023-04-14', '2023-04-24', '2023-04-23','Đã trả');
call insertBorrow('000022', 'SV0012', 'CTU0021', '2023-04-14', '2023-04-24', '2023-04-21','Đã trả');
call insertBorrow('000023', 'SV0033', 'CTU0045', '2023-04-14', '2023-04-24', '2023-04-21','Đã trả');
call insertBorrow('000024', 'SV0044', 'CTU0047', '2023-04-14', '2023-04-24', '2023-04-22','Đã trả');
call insertBorrow('000025', 'SV0028', 'CTU0047', '2023-04-14', '2023-04-24', '2023-04-22','Đã trả');
call insertBorrow('000026', 'SV0033', 'CTU0046', '2023-04-14', '2023-04-24', '2023-04-19','Đã trả');
call insertBorrow('000027', 'SV0027', 'CTU0044', '2023-04-14', '2023-04-24', '2023-04-19','Đã trả');
call insertBorrow('000028', 'SV0018', 'CTU0039', '2023-04-14', '2023-04-24', '2023-04-19','Đã trả');
call insertBorrow('000029', 'SV0022', 'CTU0041', '2023-04-14', '2023-04-24', '2023-04-21','Đã trả');
call insertBorrow('000030', 'SV0011', 'CTU0039', '2023-04-14', '2023-04-24', '2023-04-22','Đã trả');
call insertBorrow('000031', 'SV0010', 'CTU0047', '2023-04-14', '2023-04-24', '2023-04-18','Đã trả');
call insertBorrow('000032', 'SV0023', 'CTU0014', '2023-04-14', '2023-04-24', '2023-04-18','Đã trả');
call insertBorrow('000033', 'SV0024', 'CTU0015', '2023-04-15', '2023-04-25', '2023-04-19','Đã trả');
call insertBorrow('000034', 'SV0040', 'CTU0014', '2023-04-15', '2023-04-25', '2023-04-19','Đã trả');
call insertBorrow('000035', 'SV0009', 'CTU0039', '2023-04-15', '2023-04-25', '2023-04-25','Đã trả');
call insertBorrow('000036', 'SV0042', 'CTU0047', '2023-04-15', '2023-04-25', '2023-04-22','Đã trả');
call insertBorrow('000037', 'SV0042', 'CTU0046', '2023-04-15', '2023-04-25', '2023-04-20','Đã trả');
call insertBorrow('000038', 'SV0031', 'CTU0047', '2023-04-15', '2023-04-25', '2023-04-19','Đã trả');
call insertBorrow('000039', 'SV0031', 'CTU0015', '2023-04-15', '2023-04-25', '2023-04-21','Đã trả');
call insertBorrow('000040', 'SV0049', 'CTU0036', '2023-04-15', '2023-04-25', '2023-04-22','Đã trả'); -- 40 borrows
call insertBorrow('000042', 'SV0049', 'CTU0001', '2023-04-15', '2023-04-25', '2023-04-22');



DELIMITER $$
CREATE TRIGGER `check_return_date` BEFORE INSERT ON `borrow`
FOR EACH ROW
BEGIN
    IF NEW.dateReturn < NEW.dateBorrow THEN
        SIGNAL SQLSTATE '45000' 
            SET MESSAGE_TEXT = 'Invalid return date';
    END IF;
END $$
DELIMITER ;

DROP TRIGGER CHECK_RETURN_DATE_UPDATE;
DELIMITER $$
CREATE TRIGGER check_return_date_update
BEFORE UPDATE ON borrow
FOR EACH ROW
BEGIN
IF NEW.dateReturn < NEW.dateBorrow THEN
SIGNAL SQLSTATE '45000'
SET MESSAGE_TEXT = 'Invalid return date';
END IF;
END;


--  Lấy danh sách Sinh viên
DELIMITER $$
DROP PROCEDURE IF EXISTS getAllStudent $$
CREATE PROCEDURE getAllStudent()
BEGIN
	SELECT * FROM  users WHERE users.position like "Sinh viên";
END;
DELIMITER;

-- Lấy danh sách Thủ thư 
DELIMITER $$
DROP PROCEDURE IF EXISTS getAllLecturer $$
CREATE PROCEDURE getAllLecturer()
BEGIN
	SELECT * FROM  users WHERE users.position like "Thủ thư";
END;
COMMIT;
DELIMITER;


-- Thêm sách
DELIMITER $$
START TRANSACTION;
CREATE DEFINER=`root`@`localhost` PROCEDURE `insertBook`(in id_book char(7), name_book varchar(50), name_author varchar(45),
	type_book varchar(45), no_shelf varchar(7), quant int)
BEGIN
	INSERT INTO library.books values(id_book, name_book, name_author, type_book, no_shelf, quant);
END;
commit;
DELIMITER;

-- Xóa sách
DELIMITER $$
START TRANSACTION;
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteBook`(in id_book char(7))
BEGIN
	DELETE FROM library.books where books.idBook = id_book;
END;
COMMIT;
DELIMITER;

-- Sửa sách
DELIMITER $$
START TRANSACTION;
CREATE DEFINER=`root`@`localhost` PROCEDURE `updateBook`(in id_book char(7), name_book varchar(50), name_author varchar(45), type_book varchar(45), no_shelf varchar(7), quant int)
BEGIN
	UPDATE library.books set books.nameBook = name_book, books.author = name_author, books.typeBook = type_book,
    books.shelf = no_shelf, books.quantity = quant  WHERE books.idBook = id_book;
END;
COMMIT;
DELIMITER;


-- Thêm người dùng
delimiter $$
START TRANSACTION;
create definer =`root`@`localhost` procedure`insertUser`(in usrname char(6),pass char(8),na varchar(45),pos varchar(45),pho char(12))
begin
	Insert into library.users values(usrname, pass, na, pos, pho);
end;
COMMIT;
delimiter;

-- Xóa người dùng
delimiter $$
START TRANSACTION;
create definer =`root`@`localhost` procedure`deleteUser`(in usrname char(6))
begin
	delete from library.users where users.username = usrname;
end;
COMMIT;
delimiter;

-- Sửa người dùng
delimiter $$
START TRANSACTION;
create definer =`root`@`localhost` procedure`updateUser`(in usrname char(6),pass char(8),na varchar(45),pos varchar(45),pho char(12))
begin
	update library.users  set users.password=pass,users.name=na, users.position=pos,users.phone=pho where users.username=usrname;
end;
COMMIT;
delimiter;


-- Thêm mượn
delimiter $$
START TRANSACTION;
create definer =`root`@`localhost` procedure`insertBorrow`(in id char(6),na char(6), idb char(7), dateb date, dated date, dater date, tt varchar(45))
begin
	Insert into library.borrow values(id,na,idb,dateb,dated,dater,tt);
end;
COMMIT;
delimiter;

-- Xóa mượn
delimiter $$
START TRANSACTION;
create definer =`root`@`localhost` procedure`deleteBorrow`(in id char(6))
begin
	delete from library.borrow where borrow.idBorrow = id;
end;
COMMIT;
delimiter;

-- Sửa mượn
delimiter $$
START TRANSACTION;
create definer =`root`@`localhost` procedure`updateBorrow`(in id char(6),na char(6), idb char(7), dateb date, dated date, dater date,tt varchar(45))
begin
	update library.borrow
    set borrow.username = na, borrow.idBook = idb, borrow.dateBorrow=dateb,borrow.dateDue=dated , borrow.dateReturn=dater, borrow.trangthai = tt
    where borrow.idBorrow = id;
end;
COMMIT;
delimiter;

call insertBorrow('000041', 'SV0039', 'CTU0015', '2023-04-15', '2023-04-25', '2023-04-26','Đã trả');



-- Tổng số lượng sách của thư viện
delimiter $$
START TRANSACTION;
CREATE FUNCTION totalBooks()
	RETURNS int
    DETERMINISTIC
BEGIN
    RETURN (SELECT SUM(quantity) FROM books);
END;
COMMIT;
delimiter;

select totalBooks();
-- Tổng số lượng sách của một kệ 'location'
delimiter $$
CREATE FUNCTION totalBooksInShelf(location varchar(7))
	RETURNS int
    DETERMINISTIC
BEGIN
	DECLARE quant int;
	SELECT SUM(quantity) into quant FROM books
    WHERE shelf=location;
    RETURN quant;
END;
delimiter;
drop function searchUser;


-- Search người dùng
delimiter $$
CREATE DEFINER=`root`@`localhost` FUNCTION `searchUser`(uname char(6)) 
	RETURNS int
    DETERMINISTIC
begin
	if(uname in (select username from library.users))
		then return 1;
		else return 0;
	end if;
end;
delimiter;

-- Search book
delimiter $$
CREATE DEFINER=`root`@`localhost` FUNCTION `searchBook`(tensach varchar(45)) 
	RETURNS int
	DETERMINISTIC
begin
	if(tensach in (select tenSach from library.books))
then return 1;
else return 0;
end if;
end;
delimiter;




select sum(quantity) from books;
call insertUser('CB0001', '123456', 'Trương Chấn Huy', 'Thủ thư', '0903321933');
call insertUser('CB0002', '123456', 'Nguyễn Khắc Duy', 'Thủ thư', '0908152508');
call insertUser('CB0003', '1234567', 'Trần Công Nhật', 'Thủ thư', '0702965321');
call insertUser('CB0004', '1234568', 'Liên Thị Cẩm Ni', 'Thủ thư', '0903321933');
call insertUser('CB0005', '1234589', 'Tạ Trần Ngọc Xuyến', 'Thủ thư', '0908155503');
call insertUser('SV0001', '0120123', 'Nguyễn Hoàng Gia Bảo', 'Thủ thư', '0903321620');
call insertUser('SV0002', '123123', 'Trương Hoàng Huy', 'Sinh viên', '0903312082');
call insertUser('SV0003', '1234123', 'Lý Chấn Huy', 'Sinh viên', '0903962017');
call insertUser('SV0004', '321321', 'Nguyễn Thị Bảo Ngọc', 'Sinh viên', '0903410077');
call insertUser('SV0005', '6543210', 'Trương Thị Trúc Ly', 'Sinh viên', '0703992118');
call insertUser('SV0006', '890890', 'Trần Thị Kim Anh', 'Sinh viên', '0702312001');
call insertUser('SV0007', '1234500', 'Nguyễn Hoàng Nhã Trân', 'Sinh viên', '0708932000');
call insertUser('SV0008', '0123456', 'Ngô Hoàng Uyên', 'Sinh viên', '0702345620');
call insertUser('SV0009', '012012', 'Lương Chí Cao', 'Sinh viên', '0903322932');
call insertUser('SV0010', '123123', 'Trương Vạn Phát', 'Sinh viên', '0763895423');
call insertUser('SV0011', '1234123', 'Lý Văn Toàn', 'Sinh viên', '0703112003');
call insertUser('SV0012', '321321', 'Thiềm Ngọc Uyên Nhi', 'Sinh viên', '0903511067');
call insertUser('SV0013', 'trucnhi', 'Thạch Thị Trúc Nhi', 'Sinh viên', '0703192169');
call insertUser('SV0014', 'nhumai2', 'Lữ Như Mai', 'Sinh viên', '0702312000');
call insertUser('SV0015', '1234550', 'Đỗ Lý Anh Thư', 'Sinh viên', '0708533000');
call insertUser('SV0016', '0123456', 'Đỗ Quốc Cơ', 'Sinh viên', '0394398001');
call insertUser('SV0017', '654321', 'Huỳnh Tuấn Dũng', 'Sinh viên', '0703192169');
call insertUser('SV0018', '890891', 'Trần Trung', 'Sinh viên', '0702312000');
call insertUser('SV0019', '1234501', 'Đỗ Phúc Sang', 'Sinh viên', '0708533000');
call insertUser('SV0020', '0123456', 'Trần Hoàng Anh', 'Sinh viên', '0394398001');
call insertUser('SV0021', '1231231', 'Phương Trịnh Anh Thư', 'Sinh viên', '0394397003');
call insertUser('SV0022', '0123457', 'Diệp Quốc Tín', 'Sinh viên', '0394118345');
call insertUser('SV0023', '6543112', 'Huỳnh Anh Kiệt', 'Sinh viên', '0703112179');
call insertUser('SV0024', '890123', 'Cao Tiến Dũng', 'Sinh viên', '0702212123');
call insertUser('SV0025', '1234501', 'Cao Thị Mỹ Tú', 'Sinh viên', '0708563010');
call insertUser('SV0026', '1231231', 'Trịnh Hoài Nam', 'Sinh viên', '0394317009');
call insertUser('SV0027', '0123457', 'Trần Văn Nam', 'Sinh viên', '0394110344');
call insertUser('SV0028', 'ctuctu', 'Nguyễn Trọng Nhân', 'Sinh viên', '0703182279');
call insertUser('SV0029', 'cantho', 'Nguyễn Mạnh Quỳnh', 'Sinh viên', '0702922128');
call insertUser('SV0030', 'dh1234', 'Nguyễn Thị Thủy Tiên', 'Sinh viên', '0708573012');
call insertUser('SV0031', 'zxczxc', 'Nguyễn Thị Hoài Thương', 'Sinh viên', '0394210334');
call insertUser('SV0032', 'daihocc', 'Nguyễn Trọng Quý', 'Sinh viên', '0703132676');
call insertUser('SV0033', 'abcdef', 'Bùi Thanh Tùng', 'Sinh viên', '0702992198');
call insertUser('SV0034', 'tructhanh', 'Phạm Thị Thanh Trúc', 'Sinh viên', '0708553992');
call insertUser('SV0035', 'tinhngo', 'Ngô Thanh Tịnh', 'Sinh viên', '0394290333');
call insertUser('SV0036', 'daihocc', 'Bùi Thanh Xuân', 'Sinh viên', '0703134676');
call insertUser('SV0037', 'Abcdef', 'Võ Thị Ngọc Nhi', 'Sinh viên', '0394810340');
call insertUser('SV0038', 'khoi123', 'Võ Trần Khôi Nguyên', 'Sinh viên', '0708513192');
call insertUser('SV0039', 'tuongne', 'Nguyễn Bá Tường', 'Sinh viên', '0703334671');
call insertUser('SV0040', 'thepham', 'Phạm Ngọc Thể', 'Sinh viên', '0394818330');
call insertUser('SV0041', '789123', 'Châu Tuyết Nhi', 'Sinh viên', '0708503199');
call insertUser('SV0042', 'phucCT', 'Nguyễn Vạn Phúc', 'Sinh viên', '0703434476');
call insertUser('SV0043', '101010', 'Mai Công Thoại', 'Sinh viên', '0394410344');
call insertUser('SV0044', 'khanh92', 'Trần Quốc Khánh', 'Sinh viên', '0708515152');
call insertUser('SV0045', 'nhulieu', 'Nguyễn Như Liễu', 'Sinh viên', '0703964671');
call insertUser('SV0046', '1230123', 'Trương Xuân Anh', 'Sinh viên', '0394888333');
call insertUser('SV0047', '789123', 'Thái Văn Nam', 'Sinh viên', '0708563119');
call insertUser('SV0048', 'thinh10', 'Nguyễn Gia Thịnh', 'Sinh viên', '0703513152');
call insertUser('SV0049', 'vanninh', 'Tạ Văn Ninh', 'Sinh viên', '0703954672');
call insertUser('SV0050', '567567', 'Vũ Trọng Tính', 'Sinh viên', '0394898323');
call insertUser('SV0051', 'kimtho', 'Lữ Kim Thơ', 'Sinh viên', '0708585318'); -- 50 users


call insertBook('CTU0001', 'Lối sống tối giản của người Nhật', 'Sasaki Fumio', 'Lối sống', 'A1', 3);
call insertBook('CTU0002', 'Nhà giả kim', 'Paulo Coelho', 'Kỹ năng sống', 'A1', 5);
call insertBook('CTU0003', 'Trí tuệ do thái', 'Eran Katz', 'Kỹ năng sống', 'A1', 2);
call insertBook('CTU0004', 'Tâm lý học về tiền', 'Morgan Housel', 'Kỹ năng sống', 'A1', 1);
call insertBook('CTU0005', 'Còn chút gì để nhớ', 'Nguyễn Nhật Ánh', 'Văn học', 'A1', 7);
call insertBook('CTU0006', 'Mắt biếc', 'Nguyễn Nhật Ánh', 'Văn học', 'A1', 9);
call insertBook('CTU0007', 'Kiêu hãnh và định kiến', 'Jane Austen', 'Văn học', 'A1', 9);
call insertBook('CTU0008', 'Chí Phèo', 'Nam Cao', 'Văn học', 'A1', 4);
call insertBook('CTU0009', 'Tư duy như Churchill', 'Daniel Smith', 'Kỹ năng sống', 'A1', 1);
call insertBook('CTU0010', 'Ổn định hay tự do', 'Trương Học Vĩ', 'Kỹ năng sống', 'A1', 2);
call insertBook('CTU0011', 'Hoa hồng xứ khác', 'Nguyễn Nhật Ánh', 'Văn học', 'A1', 3);
call insertBook('CTU0012', 'Einstein cuộc đời và vũ trụ', 'Walter Isaacson', 'Tiểu sử', 'A1', 3);
call insertBook('CTU0013', 'Quân vương – thuật cai trị', 'Niccolò Machiavelli', 'Chính trị', 'A1', 2);
call insertBook('CTU0014', 'Giá trị của sự tử tế', 'Piero Ferrucci', 'Kỹ năng sống', 'A1', 8);
call insertBook('CTU0015', 'Những người hàng xóm', 'Nguyễn Nhật Ánh', 'Văn học', 'A1', 8);
call insertBook('CTU0016', 'Tiểu sử Steve Jobs', 'Walter Isaacson', 'Tiểu sử', 'A1', 3); 
-- A1 70 books

call insertBook('CTU0017', 'Người giàu nhất thành Babylon', 'George S. Clason', 'Kỹ năng sống', 'A2', 5);
call insertBook('CTU0018', 'Người bán hàng vĩ đại nhất thế giới', 'Og Mandino', 'Kỹ năng sống', 'A2', 3);
call insertBook('CTU0019', 'Giới tinh hoa quyền lực', 'Charles Wright Mills', 'Chính trị', 'A2', 2);
call insertBook('CTU0020', 'Bàn về khế ước xã hội', 'Jean - Jacques Rousseau', 'Chính trị', 'A2', 2);
call insertBook('CTU0021', 'Tương lai của quyền lực', 'Joseph S. Nye Jr', 'Chính trị', 'A2', 4);
call insertBook('CTU0022', 'Kiểm soát quyền lực nhà nước', 'GS. TS. Nguyễn Đăng Dung', 'Chính trị', 'A2', 4);
call insertBook('CTU0023', 'Tự chỉ trích', 'Nguyễn Văn Cừ', 'Chính trị', 'A2', 3);
call insertBook('CTU0024', 'Đường Cách Mệnh', 'Nguyễn Ái Quốc', 'Chính trị', 'A2', 2);
call insertBook('CTU0025', 'Sapiens lược sử loài người', 'Yuval Noah Harari', 'Lịch sử', 'A2', 9);
call insertBook('CTU0026', 'Homo Deus: lược sử tương lai', 'Yuval Noah Harari', 'Lịch sử', 'A2', 9);
call insertBook('CTU0027', 'Kochland - Đế chế Koch', 'Christopher Leonard', 'Lịch sử', 'A2', 2);
call insertBook('CTU0028', 'Sự giàu và nghèo của các dân tộc', 'David S.Landes', 'Lịch sử', 'A2', 7);
call insertBook('CTU0029', 'Lịch sử tiền tệ', 'Jack Weatherford', 'Lịch sử', 'A2', 4);
call insertBook('CTU0030', 'Những con đường tơ lụa', 'Peter Frankopan', 'Lịch sử', 'A2', 3);
call insertBook('CTU0031', 'Đệ nhất thế chiến', 'Robert Leckie', 'Lịch sử', 'A2', 2);
call insertBook('CTU0032', 'Lịch sử văn minh Trung Hoa', 'Will Durant', 'Lịch sử', 'A2', 5);
call insertBook('CTU0033', 'Lịch sử văn minh Ả Rập', 'Will Durant', 'Lịch sử', 'A2', 5);
call insertBook('CTU0034', 'Lịch sử văn minh Ấn Độ', 'Will Durant', 'Lịch sử', 'A2', 5);
call insertBook('CTU0035', 'Nguồn gốc văn minh', 'Will Durant', 'Lịch sử', 'A2', 5); 

call insertBook('CTU0036', 'Hai số phận', 'Jeffrey Archer', 'Văn học', 'A3', 5);
call insertBook('CTU0037', 'Một chỗ trong đời', 'Annie Ernaux', 'Văn học', 'A3', 5);
call insertBook('CTU0038', 'Tiếng núi', 'Kawabata Yasunari', 'Văn học', 'A3', 5);
call insertBook('CTU0039', 'Đồi gió hú', 'Emily Bronte', 'Văn học', 'A3', 5);
call insertBook('CTU0040', 'Túp lều Bác Tom', 'Harriet Beecher Stowe', 'Văn học', 'A3', 5);
call insertBook('CTU0041', 'Những ngôi sao Eger', 'Gárdonyi Géza', 'Văn học', 'A3', 5);
call insertBook('CTU0042', 'Cuốn theo chiều gió', 'Margaret Mitchell', 'Văn học', 'A3', 5);
call insertBook('CTU0043', 'Trà hoa nữ', 'Alexandre Dumas', 'Văn học', 'A3', 5);
call insertBook('CTU0044', 'Những người phụ nữ bé nhỏ', 'Louisa May Alcott', 'Văn học', 'A3', 5);
call insertBook('CTU0045', 'Chuông nguyện hồn ai', 'Ernest Miller Hemingway', 'Văn học', 'A3', 5);
call insertBook('CTU0046', 'Ba người lính Ngự Lâm', 'Alexandre Dumas', 'Văn học', 'A3', 10);
call insertBook('CTU0047', 'Bá tước Monte Cristo', 'Alexandre Dumas', 'Văn học', 'A3', 10); -- A3 70


call updateBorrow('000001', 'SV0001', 'CTU0001', '2023-04-01', '2023-04-11', '2023-04-10','Chưa trả');

call insertBorrow('000001', 'SV0001', 'CTU0001', '2023-04-01', '2023-04-11', '2023-04-10','Đã trả');
call insertBorrow('000002', 'SV0002', 'CTU0001', '2023-04-01', '2023-04-11', '2023-04-11','Đã trả');
call insertBorrow('000003', 'SV0003', 'CTU0005', '2023-04-02', '2023-04-12', '2023-04-11','Đã trả');
call insertBorrow('000004', 'SV0004', 'CTU0008', '2023-04-02', '2023-04-12', '2023-04-09','Đã trả');
call insertBorrow('000005', 'SV0005', 'CTU0003', '2023-04-04', '2023-04-14', '2023-04-14','Đã trả');
call insertBorrow('000006', 'SV0006', 'CTU0006', '2023-04-05', '2023-04-15', '2023-04-14','Đã trả');
call insertBorrow('000007', 'SV0007', 'CTU0004', '2023-04-06', '2023-04-16', '2023-04-15','Đã trả');
call insertBorrow('000008', 'SV0008', 'CTU0009', '2023-04-10', '2023-04-20', '2023-04-19','Đã trả');
call insertBorrow('000009', 'SV0002', 'CTU0010', '2023-04-13', '2023-04-23', '2023-04-21','Đã trả');
call insertBorrow('000010', 'SV0001', 'CTU0006', '2023-04-13', '2023-04-23', '2023-04-22','Đã trả');
call insertBorrow('000011', 'SV0027', 'CTU0047', '2023-04-13', '2023-04-23', '2023-04-15','Đã trả');
call insertBorrow('000012', 'SV0020', 'CTU0011', '2023-04-13', '2023-04-23', '2023-04-18','Đã trả');
call insertBorrow('000013', 'SV0013', 'CTU0030', '2023-04-13', '2023-04-23', '2023-04-18','Đã trả');
call insertBorrow('000014', 'SV0014', 'CTU0030', '2023-04-13', '2023-04-23', '2023-04-18','Đã trả');
call insertBorrow('000015', 'SV0035', 'CTU0013', '2023-04-13', '2023-04-23', '2023-04-23','Đã trả');
call insertBorrow('000016', 'SV0026', 'CTU0022', '2023-04-13', '2023-04-23', '2023-04-22','Đã trả');
call insertBorrow('000017', 'SV0017', 'CTU0044', '2023-04-13', '2023-04-23', '2023-04-20','Đã trả');
call insertBorrow('000018', 'SV0038', 'CTU0039', '2023-04-13', '2023-04-23', '2023-04-19','Đã trả');
call insertBorrow('000019', 'SV0019', 'CTU0019', '2023-04-13', '2023-04-23', '2023-04-21','Đã trả');
call insertBorrow('000020', 'SV0015', 'CTU0036', '2023-04-14', '2023-04-24', '2023-04-22','Đã trả');
call insertBorrow('000021', 'SV0021', 'CTU0031', '2023-04-14', '2023-04-24', '2023-04-23','Đã trả');
call insertBorrow('000022', 'SV0012', 'CTU0021', '2023-04-14', '2023-04-24', '2023-04-21','Đã trả');
call insertBorrow('000023', 'SV0033', 'CTU0045', '2023-04-14', '2023-04-24', '2023-04-21','Đã trả');
call insertBorrow('000024', 'SV0044', 'CTU0047', '2023-04-14', '2023-04-24', '2023-04-22','Đã trả');
call insertBorrow('000025', 'SV0028', 'CTU0047', '2023-04-14', '2023-04-24', '2023-04-22','Đã trả');
call insertBorrow('000026', 'SV0033', 'CTU0046', '2023-04-14', '2023-04-24', '2023-04-19','Đã trả');
call insertBorrow('000027', 'SV0027', 'CTU0044', '2023-04-14', '2023-04-24', '2023-04-19','Đã trả');
call insertBorrow('000028', 'SV0018', 'CTU0039', '2023-04-14', '2023-04-24', '2023-04-19','Đã trả');
call insertBorrow('000029', 'SV0022', 'CTU0041', '2023-04-14', '2023-04-24', '2023-04-21','Đã trả');
call insertBorrow('000030', 'SV0011', 'CTU0039', '2023-04-14', '2023-04-24', '2023-04-22','Đã trả');
call insertBorrow('000031', 'SV0010', 'CTU0047', '2023-04-14', '2023-04-24', '2023-04-18','Đã trả');
call insertBorrow('000032', 'SV0023', 'CTU0014', '2023-04-14', '2023-04-24', '2023-04-18','Đã trả');
call insertBorrow('000033', 'SV0024', 'CTU0015', '2023-04-15', '2023-04-25', '2023-04-19','Đã trả');
call insertBorrow('000034', 'SV0040', 'CTU0014', '2023-04-15', '2023-04-25', '2023-04-19','Đã trả');
call insertBorrow('000035', 'SV0009', 'CTU0039', '2023-04-15', '2023-04-25', '2023-04-25','Đã trả');
call insertBorrow('000036', 'SV0042', 'CTU0047', '2023-04-15', '2023-04-25', '2023-04-22','Đã trả');
call insertBorrow('000037', 'SV0042', 'CTU0046', '2023-04-15', '2023-04-25', '2023-04-20','Đã trả');
call insertBorrow('000038', 'SV0031', 'CTU0047', '2023-04-15', '2023-04-25', '2023-04-19','Đã trả');
call insertBorrow('000039', 'SV0031', 'CTU0015', '2023-04-15', '2023-04-25', '2023-04-21','Đã trả');
call insertBorrow('000040', 'SV0049', 'CTU0036', '2023-04-15', '2023-04-25', '2023-04-22','Đã trả');

-- tru sl sach khi them muon
CREATE TRIGGER `updateQuantity` AFTER INSERT ON `borrow`
FOR EACH ROW
UPDATE books SET quantity = quantity - 1 WHERE idBook = NEW.idBook;

DELIMITER $$
CREATE TRIGGER `update_books_quantity` AFTER UPDATE ON `borrow`
FOR EACH ROW
BEGIN
    IF OLD.trangthai <> NEW.trangthai AND NEW.trangthai = 'Đã trả' THEN
        UPDATE books SET quantity = quantity + 1 WHERE idBook = NEW.idBook;
    END IF;
END$$
DELIMITER ;


DELIMITER $$
CREATE TRIGGER `check_return_date` BEFORE 
INSERT ON `borrow`
FOR EACH ROW
BEGIN
    IF NEW.dateReturn < NEW.dateBorrow THEN
        SIGNAL SQLSTATE '45000' 
            SET MESSAGE_TEXT = 'Invalid return date';
    END IF;
END $$
DELIMITER ;


-- ngay muon > ngay tra


DELIMITER $$
CREATE TRIGGER `check_return_date_update`
BEFORE UPDATE ON borrow
FOR EACH ROW
BEGIN
	IF NEW.dateReturn < NEW.dateBorrow THEN
		SIGNAL SQLSTATE '45000'
		SET MESSAGE_TEXT = 'Invalid return date';
	END IF;
END;
DELIMTER;

drop trigger check_return_date_update;
DELIMITER $$
CREATE TRIGGER `check_return_date_insert`
BEFORE INSERT ON borrow
FOR EACH ROW
BEGIN
	IF NEW.dateReturn < NEW.dateBorrow THEN
		SIGNAL SQLSTATE '45000'
		SET MESSAGE_TEXT = 'Invalid return date';
	END IF;
END;
$$
DELIMITER ;


-- ngay muon > han tra insert
DELIMITER $$
CREATE TRIGGER `check_return_dateDue_insert` BEFORE INSERT ON `borrow`
FOR EACH ROW
BEGIN
    IF NEW.dateDue < NEW.dateBorrow THEN
        SIGNAL SQLSTATE '45000' 
            SET MESSAGE_TEXT = 'Invalid return date';
    END IF;
END $$
DELIMITER ;

-- ngay muon > han tra update
DELIMITER $$
CREATE TRIGGER `check_return_dateDue_update` BEFORE UPDATE ON `borrow`
FOR EACH ROW
BEGIN
    IF NEW.dateDue < NEW.dateBorrow THEN
        SIGNAL SQLSTATE '45000' 
            SET MESSAGE_TEXT = 'Invalid return date';
    END IF;
END $$
DELIMITER ;


DELIMITER $$
CREATE FUNCTION getTotalQuantity()
RETURNS INT DETERMINISTIC
BEGIN
    DECLARE total INT;
    SELECT SUM(quantity) INTO total FROM books;
    RETURN total;
END $$
DELIMITER ;
SELECT getTotalQuantity();