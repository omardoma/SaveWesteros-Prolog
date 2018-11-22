%% [O][E][W]
%% [W][O][E]
%% [D][E][A]

grid(3,3).
agent_capacity(2).
dragon_stone(2,0).
obstacle(0,0).
obstacle(1,1).
white_walker(0,2,s0).
white_walker(1,0,s0).
agent(2,2,0,s0).

%% Checks if all white walkers in the grid have been killed, which is my goal state
is_goal(S):-
	killed_white_walker(0,2,S),
	killed_white_walker(1,0,S).

%% Checks if a cell is in bounds of the grid size
cell_within_bounds(X,Y):-
	grid(X1,Y1),
	X < X1,
	Y < Y1,
	X >= 0,
	Y >= 0.

%% Check for the existence of a white walker
white_walker(X,Y,result(_,S)):-
	white_walker(X,Y,S),
	\+killed_white_walker(X,Y,result(_,S)).

%% Persist the killing of the white white_walker
killed_white_walker(X,Y,result(_,S)):-
	killed_white_walker(X,Y,S).

%% Checks if a white walker was killed by evaluating wether the agent
%% was in a cell next to that white walker and if yes, if he chose to kill it
killed_white_walker(X,Y,result(kill,S)):-
	white_walker(X,Y,S),
	agent(X1,Y1,DG,S),
	DG > 0,
	(((X1 is X-1; X1 is X+1), Y1 is Y);
	((Y1 is Y-1; Y1 is Y+1), X1 is X)).

agent(X,Y,DG,result(pickup,S)):-
	agent(X,Y,0,S),
	dragon_stone(X,Y),
	agent_capacity(DG).

agent(X,Y,DG,result(kill,S)):-
	agent(X,Y,DG1,S),
	DG is DG1-1,
	DG >= 0,
	white_walker(X1,Y1,S),
	(((X1 is X-1; X1 is X+1), Y1 is Y);
	((Y1 is Y-1; Y1 is Y+1), X1 is X)).

agent(X,Y,DG,result(up,S)):-
	agent(X1,Y,DG,S),
	X is X1-1,
	cell_within_bounds(X,Y),
	\+obstacle(X,Y),
	\+white_walker(X,Y,S).

agent(X,Y,DG,result(down,S)):-
	agent(X1,Y,DG,S),
	X is X1+1,
	cell_within_bounds(X,Y),
	\+obstacle(X,Y),
	\+white_walker(X,Y,S).

agent(X,Y,DG,result(left,S)):-
	agent(X,Y1,DG,S),
	Y is Y1-1,
	cell_within_bounds(X,Y),
	\+obstacle(X,Y),
	\+white_walker(X,Y,S).

agent(X,Y,DG,result(right,S)):-
	agent(X,Y1,DG,S),
	Y is Y1+1,
	cell_within_bounds(X,Y),
	\+obstacle(X,Y),
	\+white_walker(X,Y,S).

%% Generate infinite natural numbers to simulate infinity for iterative deepening
next_number(N, N).
next_number(N, M) :-
    next_number(N1, M),
    N is N1 + 1.

% Iterative Deepening implementation of the search problem
main(G,L):-
	next_number(L,0),
	call_with_depth_limit(is_goal(G),L,D),
	D \= depth_limit_exceeded,
	!.
