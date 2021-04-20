require 'gosu'

IMAGE_SIZE = 32

class Board

	def initialize
		@wsImage = Gosu::Image.new("whitestone.png")
		@bsImage = Gosu::Image.new("blackstone.png")
		@igoBoardImage = Gosu::Image.new("igoboard.png")
		@igoPlaneImage = Gosu::Image.new("igoplane.png")
		@board = Array.new(TABLE_SIZE){ Array.new(TABLE_SIZE,0)}

		@board.each_with_index{ |board,i|
			@board[i].each_with_index {|board,j|
				if i == 0 || i == (TABLE_SIZE - 1) || j == 0 || j == (TABLE_SIZE - 1) then
					@board[i][j] = -1
				end
			}
		}

		@tekazu = 1

	end

	def update(x,y)
		TABLE_SIZE.times do |i|
			TABLE_SIZE.times do |j|
				if x >= i * IMAGE_SIZE && x < (i + 1) * IMAGE_SIZE
					if y >= j * IMAGE_SIZE && y < (j + 1) * IMAGE_SIZE
						@a = i
						@b = j
					end
				end
			end
		end
		if @tekazu % 2 == 1 then
			@board[@a][@b] = 1
		else
			@board[@a][@b] = 2
		end
	end

	def addTekazu
		@tekazu += 1
	end

	def draw
		@board.each_with_index{ |board,i|
			@board[i].each_with_index {|board,j|
				if board == -1 then
					@igoPlaneImage.draw(i*32 + WIDTH_MARSINE,j*32 + WIDTH_MARSINE,0)
				elsif board == 0 then
					@igoBoardImage.draw(i*32 + WIDTH_MARSINE,j*32 + WIDTH_MARSINE,0)
				elsif board == 1 then
					@bsImage.draw(i*32 + WIDTH_MARSINE,j*32 + WIDTH_MARSINE,0)
				else 
					@wsImage.draw(i*32 + WIDTH_MARSINE ,j*32 + WIDTH_MARSINE,0)
				end
			}
		}
	end

end
