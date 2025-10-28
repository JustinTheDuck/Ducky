package Main;

import Entities.Entity;

public class CollisionChecker {
    GamePanel gp;
    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }
    public void checkTile(Entity entity) {

        int entityLeftX = entity.x + entity.solidArea.x;
        int entityRightX = entity.x + entity.solidArea.x + entity.solidArea.width;
        int entityTopY = entity.y + entity.solidArea.y;
        int entityBottomY = entity.y + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftX/gp.TileSize;
        int entityRightCol = entityRightX/gp.TileSize;
        int entityTopRow = entityTopY/gp.TileSize;
        int entityBottomRow = entityBottomY/gp.TileSize;

        int tileNum1, tileNum2;
        if (entityLeftCol >= 0 && entityRightCol < 16 && entityBottomRow < 12 && entityTopRow > 0) {
            if (entity.ySpeed == 0) {
                switch (entity.direction) {
                    case "left":
                        entityLeftCol = (entityLeftX - 24) / gp.TileSize;
                        tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                        tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                        if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                            entity.touchingX = true;
                        } else {entity.touchingX = false;}
                        break;
                    case "right":
                        entityRightCol = (entityRightX + 24) / gp.TileSize;
                        tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                        tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                        if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                            entity.touchingX = true;
                        } else {entity.touchingX = false;}
                        break;
                }
            }
            switch (entity.upDown) {
                case "up":
                    entityTopRow = Math.toIntExact((entityTopY - Math.round(entity.ySpeed)) / gp.TileSize);
                    tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                    tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                    if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                        entity.touchingY = true;
                        entity.ySpeed = 0;
                    } else {entity.touchingY = false;}
                    break;
                case "down", "null":
                    entityBottomRow = (entityBottomY + 36) / gp.TileSize;
                    if (entityBottomRow < 12) {
                        tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                        tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                        if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                            entity.touchingY = true;
                            entity.ySpeed = 0;
                        } else {
                            entity.touchingY = false;
                        }
                        break;
                    }
            }
        }

    }
}
