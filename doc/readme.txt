1. Some Important Notices:
	due to check type in runtime:
		a. thenClause and elseClause can have different type
		b. all function type is equal not matter what type their args and ret values are.
			that means: let a=fun x->1 in a:= fun y->true;a end   is syntax and type correct
	assignment is calculated right to left like c/c++
		that means let a=() in (let b=1 in a:=b:=3;b end) end  can run correctly and results in a=() and b=3

1. compare undef(such as: 1 > undef),results in runtime error

2. check type in runtime so thenClause and elseClause can have different type

3. checktype method is used to static type check, but is not perfect. It's not used in the final working version.

4. folder test and wp_test contains many test programs. Some of them are wrote by myself and some are shared from other students.
	wp_test is from Wang Peng
	queens.spl is from Lei Wei
	
5. I add an expression (wggprint expression), which use System.out.println() to print the value of that expression.
