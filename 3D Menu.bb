;3D Main Menu for SCP - Containment Breach

Global MenuMesh%,MenuCam%,MenuRoom$ ;room and camera
Global Menu3DScale#
Global MenuMisc%[10] ;NPCs and other stuff
Global MenuSprite%[3],MenuDark# = 1.0
Global MenuState#,MenuState2#
Global MenuSoundChn%,MenuSoundChn2%

Global MenuLights%[10],MenuLightSprites%[10]

Function Init3DMenu()
	
	SeedRnd MilliSecs()
	
	Select Lower(GetINIString$("MoreSCPs\BoxOfHorrors\"+OptionFile,"options","game progress"))
		Case "intro" ;173's chamber before breach (WIP)
			MenuRoom = "173"
		Case "lcz" ;Light Containment Zone rooms
			Select Rand(4)
				Case 1
					MenuRoom = "start"
				Case 2
					MenuRoom = "lockroom"
				Case 3
					MenuRoom = "roompj"
				Case 4
					MenuRoom = "914"
			End Select
		Default ;a specific room
			MenuRoom = Lower(GetINIString$("MoreSCPs\BoxOfHorrors\"+OptionFile,"options","game progress"))
	End Select
	
	If MenuRoom<>"" Then
		strMesh$ = GetINIString("Data\rooms.ini",MenuRoom,"mesh path")
		If strMesh = "" Then strMesh$ = GetINIString("Data\rooms.ini",MenuRoom,"mesh path")
		If strMesh <> "" Then
			MenuMesh = LoadRMesh_3DMenu(strMesh)
			If MenuMesh = 0 Then RuntimeError("Error loading "+MenuRoom+" (error 2)")
		Else
			RuntimeError("Error loading "+MenuRoom+" (error 1)")
		EndIf
	EndIf
	
	ScaleEntity MenuMesh,RoomScale,RoomScale,RoomScale
	MenuCam = CreateCamera()
	CameraClsColor MenuCam,0,0,0
	CameraRange MenuCam,0.1,40.0
	CameraViewport MenuCam,0,0,GraphicsWidth(),GraphicsHeight()
	MenuSprite[0] = CreateSprite(MenuCam)
	EntityOrder MenuSprite[0],-100
	ScaleSprite MenuSprite[0],0.4,0.4/650.0*228.0
	Menu3DScale = Min(1.0,Float(GraphicsHeight())/Float(GraphicsWidth()))
	MoveEntity MenuSprite[0],0.0,Menu3DScale*0.8,1.0
	Local temptex% = LoadTexture("MoreSCPs\BoxOfHorrors\GFX\menu\back3d.jpg",3)
	
	EntityTexture MenuSprite[0],temptex
	
	MenuSprite[1] = CopyEntity(MenuSprite[0])
	EntityParent MenuSprite[1],MenuCam
	EntityColor MenuSprite[1],0,0,0
	EntityAlpha MenuSprite[1],0.3
	
	EntityOrder MenuSprite[1],-99
	
	FreeTexture temptex
	
	MenuSprite[2] = CreateSprite()
	ScaleSprite MenuSprite[2],2.0,2.0
	MoveEntity MenuSprite[2],0.0,0.0,1.0
	EntityOrder MenuSprite[2],-98
	EntityColor MenuSprite[2],0,0,0
	EntityParent MenuSprite[2],MenuCam
	
	Select MenuRoom
			;intro
		Case "173"
			;[Block]
			AmbientLight 200,200,200
			TranslateEntity MenuCam,-16.0,-1.0,-8.0
			CameraFogMode MenuCam,0
			RotateEntity MenuCam,-8.5,-65.0,0
			;PointEntity MenuCam,MenuMesh
			;[End Block]
			;LCZ
		Case "start"
			;[Block]
			AmbientLight Brightness,Brightness,Brightness
			TranslateEntity MenuCam,-1.6,2.5,-3.6
			CameraFogRange MenuCam,0.1,6.0
			CameraFogMode MenuCam,1
			PointEntity MenuCam,MenuMesh
			MenuMisc[0]=LoadMesh("GFX\map\doorframe.x")
			MenuMisc[1]=LoadMesh("GFX\map\door01.x")
			;MenuMisc[2]=LoadMesh("GFX\map\Button.x")
			PositionEntity MenuMisc[0],-2.45,1.5,0.0,True
			PositionEntity MenuMisc[1],-2.45,1.5,0.0,True
			;PositionEntity MenuMisc[2],-0.6,0.7,-3.9,True
			ScaleEntity MenuMisc[0],RoomScale,RoomScale,RoomScale
			ScaleEntity MenuMisc[1],(204.0 * RoomScale) / MeshWidth(MenuMisc[1]), 312.0 * RoomScale / MeshHeight(MenuMisc[1]), 16.0 * RoomScale / MeshDepth(MenuMisc[1])
			;ScaleEntity MenuMisc[2], 0.03, 0.03, 0.03
			;RotateEntity MenuMisc[2],0,0,0,True
			RotateEntity MenuMisc[0],0,270,0,True
			RotateEntity MenuMisc[1],0,270,0,True
			EntityParent MenuMisc[0],MenuMesh
			EntityParent MenuMisc[1],MenuMesh
			;EntityParent MenuMisc[2],MenuMesh
			;[End Block]
		Case "lockroom"
			;[Block]
			AmbientLight Brightness,Brightness,Brightness
			ParticleTextures(0) = LoadTexture("GFX\smoke.png",3)
			temptex = LoadTexture("GFX\monitortexture.jpg",3)
			MenuMisc[0] = CreateSprite(MenuCam)
			ScaleSprite MenuMisc[0],(GraphicWidth/Max(GraphicWidth,GraphicHeight))*1.1,(GraphicHeight/Max(GraphicWidth,GraphicHeight))*1.1
			MoveEntity MenuMisc[0],0.0,0.0,1.0
			EntityOrder MenuMisc[0],-97
			EntityAlpha MenuMisc[0],0.5
			EntityTexture MenuMisc[0],temptex
			MenuMisc[1] = temptex
			MenuMisc[2] = LoadTexture("GFX\AIface.jpg",1)
			TranslateEntity MenuCam,-112.0 * RoomScale,1.4,112.0 * RoomScale
			PointEntity MenuCam,MenuMesh
			CameraFogRange MenuCam,0.1,6.0
			CameraFogMode MenuCam,1
			;[End Block]
		Case "roompj"
			;[Block]
			AmbientLight Brightness,Brightness,Brightness
			CameraFogRange MenuCam,0.1,6.0
			CameraFogMode MenuCam,1
			MoveEntity MenuCam,0.0,1.0,-2.0
			;[End Block]
		Case "914"
			;[Block]
			AmbientLight Brightness,Brightness,Brightness
			CameraFogRange MenuCam,0.1,6.0
			CameraFogMode MenuCam,1
			TranslateEntity MenuCam,0.0,0.2,0.1
				
			MenuMisc[0] = LoadMesh("GFX\map\914key.x")
			MenuMisc[1] = LoadMesh("GFX\map\914knob.x")
			
			For i% = 0 To 1
				ScaleEntity(MenuMisc[i], RoomScale, RoomScale, RoomScale)
				EntityFX MenuMisc[i],0
			Next
			
			PositionEntity (MenuMisc[0], 0.0, 190.0 * RoomScale, 374.0 * RoomScale)
			PositionEntity (MenuMisc[1], 0.0, 230.0 * RoomScale, 374.0 * RoomScale)
			EntityParent(MenuMisc[0], MenuMesh)
			EntityParent(MenuMisc[1], MenuMesh)
			
			MenuMisc[2]=LoadMesh("GFX\map\doorframe.x")
			MenuMisc[3]=CopyEntity(MenuMisc[2])
			PositionEntity MenuMisc[2],- 624.0 * RoomScale,0,528.0 * RoomScale,True
			PositionEntity MenuMisc[3],816.0 * RoomScale,0,528.0 * RoomScale,True
			ScaleEntity MenuMisc[2],RoomScale,RoomScale,RoomScale
			ScaleEntity MenuMisc[3],RoomScale,RoomScale,RoomScale
			EntityParent(MenuMisc[2], MenuMesh)
			EntityParent(MenuMisc[3], MenuMesh)
			
			;[End Block]
			;HCZ
			;EZ
		Case "gatea"
			TranslateEntity MenuCam,0.0,1.0,-3.8
			PointEntity MenuCam,MenuMesh
	End Select
	
	ClearTextureCache
End Function

Function DeInit3DMenu()
	
	Select MenuRoom
		Case "lockroom"
			FreeTexture ParticleTextures(0)
			FreeTexture MenuMisc[1] : MenuMisc[1]=0
			FreeTexture MenuMisc[2] : MenuMisc[2]=0
	End Select
	
	FreeEntity MenuSprite[0] : MenuSprite[0] = 0
	FreeEntity MenuSprite[1] : MenuSprite[1] = 0
	FreeEntity MenuSprite[2] : MenuSprite[2] = 0
	For i%=0 To 9
		If MenuMisc[i]<>0 Then FreeEntity MenuMisc[i] : MenuMisc[i]=0
		If MenuLights[i]<>0 Then FreeEntity MenuLights[i] : FreeEntity MenuLightSprites[i] : MenuLights[i]=0 : MenuLightSprites[i]=0
	Next
	MenuRoom = ""
	MenuState = 0 : MenuState2 = 0
	If MenuSoundChn<>0 Then
		StopChannel MenuSoundChn
		MenuSoundChn = 0
	EndIf
	If MenuSoundChn2<>0 Then
		StopChannel MenuSoundChn2
		MenuSoundChn2 = 0
	EndIf
	
	FreeEntity MenuCam : MenuCam = 0
	FreeEntity MenuMesh : MenuMesh = 0
	
	For pr.Props = Each Props
		Delete pr
	Next
	
	MenuDark = 1.0
End Function

Function Update3DMenu()
	PositionEntity MenuSprite[1],EntityX(MenuSprite[0],True),EntityY(MenuSprite[0],True),EntityZ(MenuSprite[0],True),True
;	If Rand(10)=1 Then
;		EntityAlpha MenuSprite[1],Rnd(0.5,0.8)
;		MoveEntity MenuSprite[1],Rnd(-0.1,0.1)*Menu3DScale,Rnd(-0.1,0.1)*Menu3DScale,0.0
;	Else
	EntityAlpha MenuSprite[1],0.5
	MoveEntity MenuSprite[1],0.01*Menu3DScale,-0.01*Menu3DScale,0.0
;	EndIf
	
	If MainMenuTab=0 Then
		MenuDark=Max(MenuDark-FPSfactor*0.05,0.0)
	ElseIf MainMenuTab=5 Then
		MenuDark=Min(MenuDark+FPSfactor*0.05,1.0)
		EntityAlpha MenuSprite[1],0
		EntityAlpha MenuSprite[0],1.0-MenuDark
	Else
		MenuDark=Min(MenuDark+FPSfactor*0.05,0.7)
	EndIf
	EntityAlpha MenuSprite[2],MenuDark
	
	Select MenuRoom
			;intro
		Case "173"
			;[Block]
			
			;[End Block]
			;LCZ
		Case "start"
			;[Block]
			PointEntity MenuCam,MenuMesh
			
			RotateEntity MenuCam,EntityPitch(MenuCam,True),EntityYaw(MenuCam,True)+(Sin(MenuState)*15.0),0,True
			
			MenuState=WrapAngle(MenuState+FPSfactor*0.3)
			
			MenuState2=MenuState2+FPSfactor
			If MenuState2 > 500 Then
				
				If MenuState2 > 520 And MenuState2 - FPSfactor <= 520 Then BlinkTimer = 0
				If MenuState2 < 2000 Then
					If MenuSoundChn = 0 Then
						MenuSoundChn = PlaySound(AlarmSFX(0))
					Else
						If Not ChannelPlaying(MenuSoundChn) Then MenuSoundChn = PlaySound(AlarmSFX(0))
					End If
					If Rand(600) = 1 Then tempChn%=PlaySound(IntroSFX(Rand(7, 9)))
					If Rand(400) = 1 Then tempChn%=PlaySound(IntroSFX(Rand(13, 14)))
				Else
					If Rand(1200) = 1 Then tempChn%=PlaySound(IntroSFX(Rand(7, 9)))
					If Rand(800) = 1 Then tempChn%=PlaySound(IntroSFX(Rand(13, 14)))
				EndIf
				
				If MenuState2 > 900 And MenuState2 - FPSfactor <= 900 Then MenuSoundChn2 = PlaySound(AlarmSFX(1))
				If MenuState2 > 2000 And MenuState2 - FPSfactor <= 2000 Then tempChn%=PlaySound(IntroSFX(7))
				If MenuState2 > 3500 And MenuState2 - FPSfactor <= 3500 Then tempChn%=PlaySound(IntroSFX(7))
				
				If MenuSoundChn<>0 Then ChannelVolume MenuSoundChn,0.1
				If MenuSoundChn2<>0 Then ChannelVolume MenuSoundChn2,0.1
				If tempChn<>0 Then ChannelVolume tempChn,0.1
			End If
			;[End Block]
		Case "lockroom"
			;[Block]
			PointEntity MenuCam,MenuMesh
			
			RotateEntity MenuCam,EntityPitch(MenuCam,True)-20.0,EntityYaw(MenuCam,True)+Min(Max((Sin(MenuState)*30.0),-25.0),25.0)+180.0,0,True
			
			MenuState=WrapAngle(MenuState+FPSfactor*0.35)
			
			Local p.Particles = CreateParticle(- 175.0 * RoomScale, 370.0 * RoomScale, 656.0 * RoomScale, 0, 0.03, -0.24, 200)
			p\speed = 0.05
			RotateEntity(p\pvt, 90, 0, 0, True)
			TurnEntity(p\pvt, Rnd(-20, 20), Rnd(-20, 20), 0)
			
			TurnEntity p\obj, 0,0,Rnd(360)
			
			p\SizeChange = 0.007
			
			p\Achange = -0.006
			
			p.Particles = CreateParticle(- 655.0 * RoomScale, 370.0 * RoomScale, 240.0 * RoomScale, 0, 0.03, -0.24, 200)
			p\speed = 0.05
			RotateEntity(p\pvt, 90, 0, 0, True)
			TurnEntity(p\pvt, Rnd(-20, 20), Rnd(-20, 20), 0)
			
			TurnEntity p\obj, 0,0,Rnd(360)
			
			p\SizeChange = 0.007
			
			p\Achange = -0.006
			
			If MenuSoundChn=0 Then
				MenuSoundChn = PlaySound(HissSFX)
			Else
				If Not ChannelPlaying(MenuSoundChn) Then MenuSoundChn = PlaySound(HissSFX)
			EndIf
			ChannelVolume MenuSoundChn,0.2
			
			If Rand(1000)=1 Then
				EntityTexture MenuMisc[0],MenuMisc[2]
				MenuState2=5.0
				EntityAlpha MenuMisc[0],1
			Else If MenuState2 = 0 Then
				EntityTexture MenuMisc[0],MenuMisc[1]
				EntityAlpha MenuMisc[0],0.5
			EndIf
			
			MenuState2=Max(MenuState2-FPSfactor,0)
			
			UpdateParticles()
			;[End Block]
		Case "roompj"
			;[Block]
			If Rand(300)=1 Then MenuDark = Max(MenuDark,0.6)
			;[End Block]
		Case "914"
			;[Block]
			PointEntity MenuCam,MenuMisc[0]
			
			RotateEntity MenuCam,EntityPitch(MenuCam,True),EntityYaw(MenuCam,True)+(Sin(MenuState)*15.0),0,True
			
			MenuState=WrapAngle(MenuState+FPSfactor*0.3)
			;[End Block]
			;HCZ
			;EZ
	End Select
	
	;use this code to help yourself position the camera
;	If KeyDown(17) Then ;w
;		MoveEntity MenuCam,0,0, 0.02*FPSfactor
;	EndIf
;	
;	If KeyDown(31) Then ;s
;		MoveEntity MenuCam,0,0,-0.02*FPSfactor
;	EndIf
;	
;	If KeyDown(30) Then ;a
;		MoveEntity MenuCam,-0.02*FPSfactor,0,0
;	EndIf
;	
;	If KeyDown(32) Then ;d
;		MoveEntity MenuCam,0.02*FPSfactor,0,0
;	EndIf
;	
;	If KeyDown(200) Then ;up
;		MenuState=MenuState-FPSfactor*0.5
;	EndIf
;	
;	If KeyDown(208) Then ;down
;		MenuState=MenuState+FPSfactor*0.5
;	EndIf
;	
;	If KeyDown(203) Then ;left
;		MenuState2=MenuState2+FPSfactor*0.5
;	EndIf
;	
;	If KeyDown(205) Then ;right
;		MenuState2=MenuState2-FPSfactor*0.5
;	EndIf
;	
;	RotateEntity MenuCam,MenuState,MenuState2,0
	
	RenderWorld
	
;	Color 0,0,0
;	
;	SetFont Font1
;	Text 5,5,"X = "+EntityX(MenuCam,True)+"; Y = "+EntityY(MenuCam,True)+"; Z = "+EntityZ(MenuCam,True)
;	Text 5,45,"Pitch = "+EntityPitch(MenuCam,True)+"; Yaw = "+EntityYaw(MenuCam,True)
;	
;	Color 255,255,255
End Function

Function LoadRMesh_3DMenu(file$) ;this ignores some stuff that we don't need
	
	;generate a texture made of white
	Local blankTexture%
	blankTexture=CreateTexture(4,4,1,1)
	ClsColor 255,255,255
	SetBuffer TextureBuffer(blankTexture)
	Cls
	SetBuffer BackBuffer()
	ClsColor 0,0,0
	
	;read the file
	Local f%=ReadFile(file)
	Local i%,j%,k%,x#,y#,z#,yaw#
	Local vertex%
	Local temp1i%,temp2i%,temp3i%
	Local temp1#,temp2#,temp3#
	Local temp1s$,temp2s$
	For i=0 To 3 ;reattempt up to 3 times
		If f=0 Then
			f=ReadFile(file)
		Else
			Exit
		EndIf
	Next
	If f=0 Then RuntimeError "Error reading file "+Chr(34)+file+Chr(34)
	If ReadString(f)<>"RoomMesh" Then RuntimeError Chr(34)+file+Chr(34)+" is not RMESH"
	
	DebugLog file
	file=StripFilename(file)
	
	Local count%,count2%
	;drawn meshes
	
	Local Opaque%,Alpha%
	
	Opaque=CreateMesh()
	Alpha=CreateMesh()
	
	count = ReadInt(f)
	Local childMesh%
	Local surf%,tex%[2],brush%
	
	Local isAlpha%
	
	Local u#,v#
	
	For i=1 To count ;drawn mesh
		childMesh=CreateMesh()
		
		surf=CreateSurface(childMesh)
		
		brush=CreateBrush()
		
		tex[0]=0 : tex[1]=0
		
		isAlpha=0
		For j=0 To 1
			temp1i=ReadByte(f)
			If temp1i<>0 Then
				temp1s=ReadString(f)
				tex[j]=GetTextureFromCache(temp1s)
				If tex[j]=0 Then ;texture is not in cache
					Select True
						Case temp1i<3
							tex[j]=LoadTexture(file+temp1s,1)
						Default
							tex[j]=LoadTexture(file+temp1s,3)
					End Select
					
					If tex[j]<>0 Then
						If temp1i=1 Then TextureBlend tex[j],5
						AddTextureToCache(tex[j])
						DebugLog StripPath(TextureName(tex[j]))
					EndIf
					
				EndIf
				If tex[j]<>0 Then
					isAlpha=2
					If temp1i=3 Then isAlpha=1
					
					TextureCoords tex[j],1-j
				EndIf
			EndIf
		Next
		
		If isAlpha=1 Then
			If tex[1]<>0 Then
				TextureBlend tex[1],2
				BrushTexture brush,tex[1],0,0
			Else
				BrushTexture brush,blankTexture,0,0
			EndIf
		Else
			
;			If BumpEnabled And temp1s<>"" Then
;				bumptex = GetBumpFromCache(temp1s)	
;			Else
;				bumptex = 0
;			EndIf
			
;			If bumptex<>0 Then 
;			BrushTexture brush, tex[1], 0, 0	
;			BrushTexture brush, bumptex, 0, 1
;			If tex[0]<>0 Then 
;				BrushTexture brush, tex[0], 0, 2	
;			Else
;				BrushTexture brush,blankTexture,0,2
;			EndIf
;			Else
			For j=0 To 1
				If tex[j]<>0 Then
					BrushTexture brush,tex[j],0,j
				Else
					BrushTexture brush,blankTexture,0,j
				EndIf
			Next				
;			EndIf
			
		EndIf
		
		surf=CreateSurface(childMesh)
		
		If isAlpha>0 Then PaintSurface surf,brush
		
		FreeBrush brush : brush = 0
		
		count2=ReadInt(f) ;vertices
		
		For j%=1 To count2
			;world coords
			x=ReadFloat(f) : y=ReadFloat(f) : z=ReadFloat(f)
			vertex=AddVertex(surf,x,y,z)
			
			;texture coords
			For k%=0 To 1
				u=ReadFloat(f) : v=ReadFloat(f)
				VertexTexCoords surf,vertex,u,v,0.0,k
			Next
			
			;colors
			temp1i=ReadByte(f)
			temp2i=ReadByte(f)
			temp3i=ReadByte(f)
			VertexColor surf,vertex,temp1i,temp2i,temp3i,1.0
		Next
		
		count2=ReadInt(f) ;polys
		For j%=1 To count2
			temp1i = ReadInt(f) : temp2i = ReadInt(f) : temp3i = ReadInt(f)
			AddTriangle(surf,temp1i,temp2i,temp3i)
		Next
		
		If isAlpha=1 Then
			AddMesh childMesh,Alpha
		Else
			AddMesh childMesh,Opaque
		EndIf
		EntityParent childMesh,Opaque
		EntityAlpha childMesh,0.0
		EntityType childMesh,HIT_MAP
		EntityPickMode childMesh,2
		
	Next
	
	If BumpEnabled Then; And 0 Then
;		For i = 1 To CountSurfaces(Opaque)
;			surf = GetSurface(Opaque,i)
;			brush = GetSurfaceBrush(surf)
;			tex[0] = GetBrushTexture(brush,1)
;			temp1s$ =  StripPath(TextureName(tex[0]))
;			DebugLog temp1s$
;			If temp1s$<>0 Then 
;				mat.Materials=GetCache(temp1s)
;				If mat<>Null Then
;					If mat\Bump<>0 Then
;						tex[1] = GetBrushTexture(brush,0)
;						
;						BrushTexture brush, tex[1], 0, 2	
;						BrushTexture brush, mat\Bump, 0, 1
;						BrushTexture brush, tex[0], 0, 0					
;						
;						PaintSurface surf,brush
;						
;						;If tex[1]<>0 Then DebugLog "lkmlkm" : FreeTexture tex[1] : tex[1]=0
;					EndIf
;				EndIf
;				
;				;If tex[0]<>0 Then DebugLog "sdfsf" : FreeTexture tex[0] : tex[0]=0
;			EndIf
;			If brush<>0 Then FreeBrush brush : brush=0
;		Next
;		
		For i = 2 To CountSurfaces(Opaque)
			sf = GetSurface(Opaque,i)
			b = GetSurfaceBrush( sf )
			If b<>0 Then
				t = GetBrushTexture(b, 1)
				If t<>0 Then
					texname$ =  StripPath(TextureName(t))
					
					For mat.Materials = Each Materials
						If texname = mat\name Then
							If mat\Bump <> 0 Then 
								t1 = GetBrushTexture(b,0)
								
								If t1<>0 Then
									BrushTexture b, t1, 0, 1 ;light map
									BrushTexture b, mat\Bump, 0, 0 ;bump
									BrushTexture b, t, 0, 2 ;diff
									
									PaintSurface sf,b
									
									FreeTexture t1
								EndIf
								
								;If t1<>0 Then FreeTexture t1
								;If t2 <> 0 Then FreeTexture t2						
							EndIf
							Exit
						EndIf 
					Next
					
					FreeTexture t
				EndIf
				FreeBrush b
			EndIf
			
		Next
	EndIf
	
;	Local hiddenMesh%
;	hiddenMesh=CreateMesh()
	
	count=ReadInt(f) ;invisible collision mesh
	For i%=1 To count
		;surf=CreateSurface(hiddenMesh)
		count2=ReadInt(f) ;vertices
		For j%=1 To count2
			;world coords
			x=ReadFloat(f) : y=ReadFloat(f) : z=ReadFloat(f)
			;vertex=AddVertex(surf,x,y,z)
		Next
		
		count2=ReadInt(f) ;polys
		For j%=1 To count2
			temp1i = ReadInt(f) : temp2i = ReadInt(f) : temp3i = ReadInt(f)
			;AddTriangle(surf,temp1i,temp2i,temp3i)
		Next
	Next
	
	count=ReadInt(f) ;point entities
	
	Local lightTex% = LoadTexture("GFX\light1.jpg", 1)
	
	For i%=1 To count
		temp1s=ReadString(f)
		Select temp1s
			Case "screen"
				
				temp1=ReadFloat(f);*RoomScale
				temp2=ReadFloat(f);*RoomScale
				temp3=ReadFloat(f);*RoomScale
				
				temp2s=ReadString(f)
				
;				If temp1<>0 Or temp2<>0 Or temp3<>0 Then 
;					Local ts.TempScreens = New TempScreens	
;					ts\x = temp1
;					ts\y = temp2
;					ts\z = temp3
;					ts\imgpath = temp2s
;					ts\roomtemplate = rt
;				EndIf
				
			Case "waypoint"
				
				temp1=ReadFloat(f);*RoomScale
				temp2=ReadFloat(f);*RoomScale
				temp3=ReadFloat(f);*RoomScale
				
;				Local w.TempWayPoints = New TempWayPoints
;				w\roomtemplate = rt
;				w\x = temp1
;				w\y = temp2
;				w\z = temp3
				
			Case "light"
				
				temp1=ReadFloat(f);*RoomScale
				temp2=ReadFloat(f);*RoomScale
				temp3=ReadFloat(f);*RoomScale
				
				Local canAddLight% = -1
				For i=0 To 9
					If MenuLights[i]=0 Then canAddLight=i : Exit
				Next
				
				If (temp1<>0 Or temp2<>0 Or temp3<>0) And (canAddLight>-1) Then 
					range# = ReadFloat(f)/2000.0
					lcolor$=ReadString(f)
					intensity# = Min(ReadFloat(f)*0.8,1.0)
					r%=Int(Piece(lcolor,1," "))*intensity
					g%=Int(Piece(lcolor,2," "))*intensity
					b%=Int(Piece(lcolor,3," "))*intensity
					
					MenuLights[canAddLight]=CreateLight(2)
					LightRange(MenuLights[canAddLight],range)
					LightColor(MenuLights[canAddLight],r,g,b)
					EntityParent(MenuLights[canAddLight],Opaque)
					PositionEntity(MenuLights[canAddLight],temp1,temp2,temp3,True)
					
					;room\LightIntensity[i] = (r+g+b)/255.0/3.0
					
					MenuLightSprites[canAddLight]= CreateSprite()
					ScaleSprite(MenuLightSprites[canAddLight], 0.13 , 0.13)
					EntityTexture(MenuLightSprites[canAddLight], lightTex)
					EntityBlend (MenuLightSprites[canAddLight], 3)
					EntityParent(MenuLightSprites[canAddLight],Opaque)
					PositionEntity(MenuLightSprites[canAddLight], temp1, temp2, temp3,True)
					;AddTempLight(rt, temp1,temp2,temp3, 2, range, r,g,b)
				Else
					ReadFloat(f) : ReadString(f) : ReadFloat(f)
				EndIf
				
			Case "spotlight"
				
				temp1=ReadFloat(f);*RoomScale
				temp2=ReadFloat(f);*RoomScale
				temp3=ReadFloat(f);*RoomScale
				
				canAddLight% = -1
				For i=0 To 9
					If MenuLights[i]=0 Then canAddLight=i : Exit
				Next
				
				If (temp1<>0 Or temp2<>0 Or temp3<>0) And (canAddLight>-1) Then 
					range# = ReadFloat(f)/2000.0
					lcolor$=ReadString(f)
					intensity# = Min(ReadFloat(f)*0.8,1.0)
					r%=Int(Piece(lcolor,1," "))*intensity
					g%=Int(Piece(lcolor,2," "))*intensity
					b%=Int(Piece(lcolor,3," "))*intensity
					
;					Local lt.LightTemplates = AddTempLight(rt, temp1,temp2,temp3, 2, range, r,g,b)
					angles$=ReadString(f)
					pitch#=Piece(angles,1," ")
					yaw#=Piece(angles,2," ")
;					lt\pitch = pitch
;					lt\yaw = yaw
;					
					innerconeangle% = ReadInt(f)
					outerconeangle% = ReadInt(f)
					
					MenuLights[canAddLight]=CreateLight(3)
					LightRange(MenuLights[canAddLight],range)
					LightColor(MenuLights[canAddLight],r,g,b)
					EntityParent(MenuLights[canAddLight],Opaque)
					LightConeAngles(MenuLightSprites[canAddLight], innerconeangle, outerconeangle)
					RotateEntity(MenuLightSprites[canAddLight], pitch, yaw, 0)
					PositionEntity(MenuLights[canAddLight],temp1,temp2,temp3,True)
					
					MenuLightSprites[canAddLight]= CreateSprite()
					ScaleSprite(MenuLightSprites[canAddLight], 0.13 , 0.13)
					EntityTexture(MenuLightSprites[canAddLight], lightTex)
					EntityBlend (MenuLightSprites[canAddLight], 3)
					EntityParent(MenuLightSprites[canAddLight],Opaque)
					PositionEntity(MenuLightSprites[canAddLight], temp1, temp2, temp3,True)
					
				Else
					ReadFloat(f) : ReadString(f) : ReadFloat(f) : ReadString(f) : ReadInt(f) : ReadInt(f)
				EndIf
				
			Case "soundemitter"
				
				temp1i=0
				
;				For j = 0 To 3
;					If rt\TempSoundEmitter[j]=0 Then
;						rt\TempSoundEmitterX[j]=ReadFloat(f)*RoomScale
;						rt\TempSoundEmitterY[j]=ReadFloat(f)*RoomScale
;						rt\TempSoundEmitterZ[j]=ReadFloat(f)*RoomScale
;						rt\TempSoundEmitter[j]=ReadInt(f)
;						
;						rt\TempSoundEmitterRange[j]=ReadFloat(f)
;						temp1i=1
;						Exit
;					EndIf
;				Next
;				
;				If temp1i=0 Then
				ReadFloat(f)
				ReadFloat(f)
				ReadFloat(f)
				ReadInt(f)
				ReadFloat(f)
;				EndIf
				
			Case "playerstart"
				
				temp1=ReadFloat(f) : temp2=ReadFloat(f) : temp3=ReadFloat(f)
				
				angles$=ReadString(f)
				pitch#=Piece(angles,1," ")
				yaw#=Piece(angles,2," ")
				roll#=Piece(angles,3," ")
				If cam Then
					PositionEntity cam,temp1,temp2,temp3
					RotateEntity cam,pitch,yaw,roll
				EndIf
			Case "model"
				file = ReadString(f)
				If file<>""
					DebugLog "file: "+file
					Local model = CreatePropObj("GFX\Map\Props\"+file);LoadMesh("GFX\Map\Props\"+file)
					
					
					temp1=ReadFloat(f) : temp2=ReadFloat(f) : temp3=ReadFloat(f)
					PositionEntity model,temp1,temp2,temp3
					
					
					temp1=ReadFloat(f) : temp2=ReadFloat(f) : temp3=ReadFloat(f)
					RotateEntity model,temp1,temp2,temp3
					
					temp1=ReadFloat(f) : temp2=ReadFloat(f) : temp3=ReadFloat(f)
					ScaleEntity model,temp1,temp2,temp3
					DebugLog "size: "+temp1+", "+temp2+", "+temp3
					
					EntityParent model,Opaque
					EntityType model,HIT_MAP
					EntityPickMode model,2
				Else
					DebugLog "file = 0"
					;Stop
				EndIf
				
			Case "ladder"
				
				count2=ReadInt(f)
				
				;If ladder = 0 Then ladder = CreateMesh() : EntityAlpha ladder,0
				
				;surf = CreateSurface(ladder)
				
				For j=1 To count2
					temp1=ReadFloat(f) : temp2=ReadFloat(f) : temp3=ReadFloat(f)
					;AddVertex surf,temp1,temp2,temp3
				Next
				
				count2=ReadInt(f)
				
				For j=1 To count2
					temp1i=ReadInt(f) : temp2i=ReadInt(f) : temp3i=ReadInt(f)
					;AddTriangle(surf,temp1i,temp2i,temp3i)
				Next
		End Select
	Next
	
	FreeTexture lightTex
	
	Local obj%
	
	temp1i=CopyMesh(Alpha)
	FlipMesh temp1i
	AddMesh temp1i,Alpha
	FreeEntity temp1i
	
	If brush <> 0 Then FreeBrush brush
	
	AddMesh Alpha,Opaque
	FreeEntity Alpha
	
	EntityFX Opaque,3
	
	;EntityAlpha hiddenMesh,0.0
	EntityAlpha Opaque,1.0
	
	;EntityType Opaque,HIT_MAP
	;EntityType hiddenMesh,HIT_MAP
	FreeTexture blankTexture
	
;	obj=CreatePivot()
;	CreatePivot(obj) ;skip "meshes" object
;	EntityParent Opaque,obj
;	EntityParent hiddenMesh,obj
;	CreatePivot(Room) ;skip "pointentites" object
;	CreatePivot(Room) ;skip "solidentites" object
	
	CloseFile f
	
	Return Opaque
	
End Function

;eof
;~IDEal Editor Parameters:
;~F#4D#6D#7F#86#D2#EF#111#143#147
;~C#Blitz3D